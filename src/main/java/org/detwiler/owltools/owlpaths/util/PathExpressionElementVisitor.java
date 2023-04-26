package org.detwiler.owltools.owlpaths.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.detwiler.owltools.owlpaths.*;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PathExpressionElementVisitor implements PathExpressionVisitor {
    private OWLReasoner reasoner;
    private OWLOntology ontology;
    private OWLDataFactory factory;
    private OWLOntologyManager manager;
    private PrefixManager prefManager;
    //private Map<String, String> prefix_map;

    //private String invPattern = "^\\[INV=(.*)\\]$";
    //private String supPattern = "^\\[SUP=(.*)\\]$";
    private Pattern invPattern = Pattern.compile("^\\[INV=(.*)\\]$");
    private Pattern supPattern = Pattern.compile("^\\[SUP=(.*)\\]$");

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
        System.out.println("starting now ...");
        //return node.getClass().getExp().jjtAccept(this, data);
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

        Set<OWLClass> leftResults = (Set<OWLClass>)child1.jjtAccept(this,data);
        PathNode child2 = (PathNode) node.jjtGetChild(1);
        Set<OWLClass> rightResults = null;

        switch(operator) {
            case "/":
                rightResults = (Set<OWLClass>)child2.jjtAccept(this,leftResults);
                return rightResults;
                //break;
            case "|":
                rightResults = (Set<OWLClass>)child2.jjtAccept(this,data);
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

        Set<OWLClass> fullResults = (Set<OWLClass>)child.jjtAccept(this,data);


        /*
        Iterator<OWLClass> objectsIt = fullResults.iterator();
        while(objectsIt.hasNext()){
            OWLClass currObj = objectsIt.next();
            if(invPropIRI != null) {
                // check to see if inverse is populated
                //if(containsRestriction(currObj, invPropIRI, child))
            }
        }

         */

        return fullResults;
    }

    @Override
    public Object visit(ASTFullIRIExpr node, Object data) {
        Set<OWLClass> fullResults = this.getObjects((Set<OWLClass>) data, node.getOperator(), node.getQualifiers());
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
            return new HashSet<OWLClass>();
        }

        Set<OWLClass> fullResults = this.getObjects((Set<OWLClass>) data, fullIRIString, node.getQualifiers());
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
        boolean isTransitive = false;
        if(operator.equals("*")||operator.equals("+")){
            isTransitive = true;
        }

        if(operator.equals("*")||operator.equals("?")){
            includeZero = true;
        }
        //TODO: deal with invalid operator
        PathNode child = (PathNode) node.jjtGetChild(0);
        Set<OWLClass> subjClses = (Set<OWLClass>) data;
        //TODO: deal with empty subjClses
        if(isTransitive){
              return transitiveClosure(subjClses, child, includeZero);
        }
        //TODO THIS IS UNFINISHED
        //TODO: Handle operation

        Set<OWLClass> results = new HashSet<OWLClass>();
        if(includeZero)
            results.addAll(subjClses);
        Set<OWLClass> childResults = (Set<OWLClass>)child.jjtAccept(this,subjClses);
        results.addAll(childResults);
        return results;
    }

    /*
    private void getQualifiers(PathNode node){
        // YOU ARE HERE, deal with qualifiers TODO: move this to child processing functions
        //TODO deal with qualifiers
        String invQualExp = node.getInvQual();
        String supQualExp = node.getSupQual();

        //TODO: I'm going to have to change this so that I pass qualifiers with the property IRI,
        // otherwise I won't be able to enforce the inv qualifier
        String invPropIRI = null;
        if(invQualExp!=null) {
            Matcher invMatcher = invPattern.matcher(invQualExp);
            if (invMatcher.find()) {
                invPropIRI = invMatcher.group(1);
                System.err.println("found inverse qualifier " + invPropIRI);
            }
        }
        String supClassIRI = null;
        if(supQualExp!=null) {
            Matcher supMatcher = invPattern.matcher(supQualExp);
            if (supMatcher.find()) {
                supClassIRI = supMatcher.group(1);
                System.err.println("found superclass qualifier " + supClassIRI);
            }
        }
    }

     */

    private Set<OWLClass> transitiveClosure(Set<OWLClass> subjClses, PathNode pathNode, boolean includeZero) {
        Set<OWLClass> results = new HashSet<OWLClass>();
        if(includeZero)
            results.addAll(subjClses);

        // YOU ARE HERE!!!
        Set<OWLClass> subjOfCurrIter = subjClses;
        while(!subjOfCurrIter.isEmpty()){
            subjOfCurrIter = (Set<OWLClass>) pathNode.jjtAccept(this, subjOfCurrIter);
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

    private Set<OWLClass> getObjects(Set<OWLClass> subjClses, String inPropIRI, Qualifiers quals) {
        Set<OWLClass> allObjClses = new HashSet<>();

        /*
        // add processing for properties with a target class constraint
        String objSupIRI = null;
        String tmpInvPropIRI = null;
        int delInd = inPropIRI.length();
        if(inPropIRI.contains("?")) {
            delInd = inPropIRI.lastIndexOf("?");
        }

        String inPropQuery = null;
        try {
            URI inProp = new URI(inPropIRI);
            inPropQuery = inProp.getQuery();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(inPropQuery!=null){
            List<NameValuePair> params = URLEncodedUtils.parse(inPropQuery, Charset.forName("UTF-8"));
            for (NameValuePair param : params) {
                //System.out.println(param.getName() + " : " + param.getValue());
                if(param.getName().equals("sup")){
                    objSupIRI = param.getValue();
                }
                else if(param.getName().equals("inv")){
                    tmpInvPropIRI = param.getValue();
                }
            }
        }

        final String propIRI = inPropIRI.substring(0,delInd);
        final OWLClass objSupCls = null!=objSupIRI?factory.getOWLClass(objSupIRI):null;
        final String invPropIRI = tmpInvPropIRI;
        // end processing for properties with a target class constraint

         */

        // add processing for qualifiers
        final String propIRI = inPropIRI;

        String objSupIRI = quals.getQualifier(Qualifiers.QualType.SUP);
        final OWLClass objSupCls = null!=objSupIRI?factory.getOWLClass(getFullIRI(objSupIRI)):null;

        final String invPropIRI = quals.getQualifier(Qualifiers.QualType.INV);
        // end processing qualifiers

        for(OWLClass subjectClass : subjClses){
            Set<OWLClass> superClses = EntitySearcher.getSuperClasses(subjectClass, ontology)
                    .filter(expr -> {
                        if (expr.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)) {
                            OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                            OWLClassExpression filler = someExpr.getFiller();


                            if (someExpr.getProperty().isNamed()) {
                                if (someExpr.getProperty().getNamedProperty().getIRI().getIRIString().equals(propIRI)) {
                                    // add processing for properties with a target class constraint
                                    OWLClass currObj = filler.asOWLClass();
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

                                    if(satisfiesSup && satisfiesInv)
                                        return true;
                                    //else
                                    //return false;
                                }
                            }
                        }
                        return false;
                    })
                    .map(expr -> {
                        OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                        OWLClassExpression filler = someExpr.getFiller();

                        return filler.asOWLClass(); // TODO: this may fail for class expressions
                    })
                    .collect(Collectors.toSet());
            allObjClses.addAll(superClses);
        }
        return allObjClses;
    }

    private boolean containsRestriction(OWLClass subjectClass, String propIRI, OWLClass objectClass) {
        Set<String> superClsIRIs = EntitySearcher.getSuperClasses(subjectClass, reasoner.getRootOntology())
                .filter(expr -> {
                    if (expr.getClassExpressionType().equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM) /*instanceof OWLObjectSomeValuesFrom*/) {
                        OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                        if (someExpr.getProperty().isNamed()) {
                            if (someExpr.getProperty().getNamedProperty().getIRI().getIRIString().equals(propIRI)&&
                                    someExpr.getFiller().equals(objectClass)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .map(expr -> {
                    OWLObjectSomeValuesFrom someExpr = (OWLObjectSomeValuesFrom) expr;
                    OWLClassExpression filler = someExpr.getFiller();
                    if(filler.isNamed())
                        return filler.asOWLClass().getIRI().getIRIString();
                    return filler.toString();
                })
                .collect(Collectors.toSet());

        if(!superClsIRIs.isEmpty())
            return true;
        return false;
    }
}
