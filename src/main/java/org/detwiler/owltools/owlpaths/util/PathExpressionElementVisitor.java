package org.detwiler.owltools.owlpaths.util;

import org.detwiler.owltools.owlpaths.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.search.EntitySearcher;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PathExpressionElementVisitor implements PathExpressionVisitor {
    private final OWLReasoner reasoner;
    private final OWLOntology ontology;
    private final OWLDataFactory factory;
    private final OWLOntologyManager manager;
    private PrefixManager prefManager;
    private final Pattern invPattern = Pattern.compile("^\\[INV=(.*)\\]$");
    private final Pattern supPattern = Pattern.compile("^\\[SUP=(.*)\\]$");


    public PathExpressionElementVisitor(OWLReasoner reasoner) {
        this.reasoner = reasoner;
        this.ontology = reasoner.getRootOntology();
        this.factory = reasoner.getRootOntology().getOWLOntologyManager().getOWLDataFactory();
        this.manager = ontology.getOWLOntologyManager();

        OWLDocumentFormat format = manager.getOntologyFormat(ontology);
        if (format.isPrefixOWLDocumentFormat()) {
            prefManager = format.asPrefixOWLDocumentFormat();
        }
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        return data;
    }

    @Override
    public Object visit(ASTStart node, Object data) {
        PathNode child = (PathNode) node.jjtGetChild(0);
        return child.jjtAccept(this,data);
    }

    @Override
    public Object visit(ASTBinaryOpExpr node, Object data) {
        String operator = node.getOperator();

        PathNode child1 = (PathNode) node.jjtGetChild(0);

        if(operator == null){
            return child1.jjtAccept(this,data);
        }

        Set<OWLClassExpression> leftResults = (Set<OWLClassExpression>)child1.jjtAccept(this,data);
        PathNode child2 = (PathNode) node.jjtGetChild(1);
        Set<OWLClassExpression> rightResults = null;

        switch(operator) {
            case "/":
                rightResults = (Set<OWLClassExpression>)child2.jjtAccept(this,leftResults);
                return rightResults;
                //break;
            case "|":
                rightResults = (Set<OWLClassExpression>)child2.jjtAccept(this,data);
                leftResults.addAll(rightResults);
                return leftResults;
                //break;
            default:
                return null;
        }
    }

    @Override
    public Object visit(ASTPropertyExpr node, Object data) {
        PathNode child = (PathNode) node.jjtGetChild(0);

        Set<OWLClassExpression> fullResults = (Set<OWLClassExpression>)child.jjtAccept(this,data);


        return fullResults;
    }

    @Override
    public Object visit(ASTFullIRIExpr node, Object data) {
        Set<OWLClassExpression> fullResults = this.getObjects((Set<OWLClassExpression>) data, node.getOperator(), node.getQualifiers());
        return fullResults;
    }

    @Override
    public Object visit(ASTPrefixIRIExpr node, Object data) {
        String prefIRIString = node.getOperator();
        String fullIRIString = null;
        try {
            fullIRIString = prefManager.getIRI(prefIRIString).getIRIString();
        } catch (OWLRuntimeException e) {
            System.err.println("prefix not found for IRI: "+prefIRIString);
            return new HashSet<OWLClassExpression>();
        }

        Set<OWLClassExpression> fullResults = this.getObjects((Set<OWLClassExpression>) data, fullIRIString, node.getQualifiers());
        return fullResults;
    }

    @Override
    public Object visit(ASTUnaryOpExpr node, Object data) {
        String operator = node.getOperator();

        //TODO: I don't know why this would be needed
        if(operator == null && node.jjtGetNumChildren()>0){  // TODO: num children argument may be uneccesary or require 0 length alternative
            return node.jjtGetChild(0).jjtAccept(this,data);
        }

        boolean includeZero = false;
        boolean isTransitive = operator.equals("*") || operator.equals("+");

        if(operator.equals("*")||operator.equals("?")){
            includeZero = true;
        }
        //TODO: deal with invalid operator
        PathNode child = (PathNode) node.jjtGetChild(0);
        Set<OWLClassExpression> subjClses = (Set<OWLClassExpression>) data;
        //TODO: deal with empty subjClses
        if(isTransitive){
              return transitiveClosure(subjClses, child, includeZero);
        }

        Set<OWLClassExpression> results = new HashSet<OWLClassExpression>();
        if(includeZero)
            results.addAll(subjClses);
        Set<OWLClassExpression> childResults = (Set<OWLClassExpression>)child.jjtAccept(this,subjClses);
        results.addAll(childResults);
        return results;
    }

    private Set<OWLClassExpression> transitiveClosure(Set<OWLClassExpression> subjClses, PathNode pathNode, boolean includeZero) {
        Set<OWLClassExpression> results = new HashSet<OWLClassExpression>();
        if(includeZero)
            results.addAll(subjClses);

        Set<OWLClassExpression> subjOfCurrIter = subjClses;
        while(!subjOfCurrIter.isEmpty()){
            subjOfCurrIter = (Set<OWLClassExpression>) pathNode.jjtAccept(this, subjOfCurrIter);
            results.addAll(subjOfCurrIter);
        }

        return results;
    }

    private String getFullIRI(String iriExpr){
        String prefIRIString = iriExpr;
        String fullIRIString = null;
        try {
            fullIRIString = prefManager.getIRI(prefIRIString).getIRIString();
        } catch (Exception e) {
            // this is not a prefix IRI
            return prefIRIString;
        }

        return fullIRIString;
    }

    private Set<OWLClassExpression> getObjects(Set<OWLClassExpression> subjClses, String inPropIRI, Qualifiers quals) {
        Set<OWLClassExpression> allObjClses = new HashSet<>();

        // add processing for qualifiers
        final String propIRI = inPropIRI;

        String objSupIRI = quals.getQualifier(Qualifiers.QualType.SUP);
        final OWLClass objSupCls = null!=objSupIRI?factory.getOWLClass(getFullIRI(objSupIRI)):null;

        final String invPropIRI = quals.getQualifier(Qualifiers.QualType.INV);
        // end processing qualifiers

        for(OWLClassExpression subjectClass : subjClses){
            Set<OWLClassExpression> superClses = EntitySearcher.getSuperClasses(subjectClass.asOWLClass(), ontology)
            //Set<OWLClassExpression> superClses = reasoner.getSuperClasses(subjectClass).entities()
                    .filter(expr -> {
                        if (expr.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)) {
                            OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                            OWLClassExpression filler = someExpr.getFiller();


                            if (someExpr.getProperty().isNamed()) {
                                if (someExpr.getProperty().getNamedProperty().getIRI().getIRIString().equals(propIRI)) {
                                    // add processing for properties with a target class constraint
                                    OWLClassExpression currObj = filler;
                                    boolean satisfiesSup = true;
                                    if(objSupCls!=null){
                                        NodeSet<OWLClass> objSupClses = reasoner.getSuperClasses(currObj);//.collect(Collectors.toSet());
                                        if(!objSupClses.containsEntity(objSupCls))
                                            satisfiesSup = false;
                                    }
                                    boolean satisfiesInv = true;
                                    if(invPropIRI!=null) {
                                        if(!containsRestriction(currObj, this.getFullIRI(invPropIRI), subjectClass)){
                                            satisfiesInv = false;
                                        }
                                    }
                                    // end processing for properties with a target class constraint

                                    return satisfiesSup && satisfiesInv;
                                }
                            }
                        }
                        return false;
                    })
                    .map(expr -> {
                        OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                        OWLClassExpression filler = someExpr.getFiller();

                        return filler;
                    })
                    .collect(Collectors.toSet());
            allObjClses.addAll(superClses);
        }
        return allObjClses;
    }

    private boolean containsRestriction(OWLClassExpression subjectClass, String propIRI, OWLClassExpression objectClass) {
        Set<OWLClassExpression> superClses = EntitySearcher.getSuperClasses(subjectClass.asOWLClass(), ontology)
        //Set<OWLClassExpression> superClses = reasoner.getSuperClasses(subjectClass).entities()
                .filter(expr -> {
                    if (expr.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM) /*instanceof OWLObjectSomeValuesFrom*/) {
                        OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                        if (someExpr.getProperty().isNamed()) {
                            return someExpr.getProperty().getNamedProperty().getIRI().getIRIString().equals(propIRI) &&
                                    someExpr.getFiller().equals(objectClass);
                        }
                    }
                    return false;
                })
                .map(expr -> {
                    OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                    OWLClassExpression filler = someExpr.getFiller();
                    return filler;
                })
                .collect(Collectors.toSet());

        return !superClses.isEmpty();
    }
}
