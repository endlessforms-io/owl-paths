package org.detwiler.owltools.owlpaths;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.AutoIRIMapper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PathExpressionTest {

    private OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
    private OWLDataFactory factory = manager.getOWLDataFactory();
    private OWLOntology ontology = null;
    private OWLReasoner reasoner = null;

    public PathExpressionTest(){
        // load ontology reasoner
        File ontFile = new File("ont/testont.owl");

        // NOTE: the following does note work with Turtle files
        AutoIRIMapper mapper = new AutoIRIMapper(ontFile.getAbsoluteFile().getParentFile(), false);
        manager.getIRIMappers().add(mapper);

        try {
            ontology = manager.loadOntologyFromOntologyDocument(ontFile);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // load the ontology to the reasoner
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        reasoner = reasonerFactory.createReasoner(ontology);
    }

    @org.junit.jupiter.api.Test
    void processPath() {
        // set up subjects
        OWLClass start = factory.getOWLClass("http://www.semanticweb.org/detwiler/ontologies/2023/4/owlpathstest#ExampleSource");
        Set<OWLClass> subjects = new HashSet<>();
        subjects.add(start);

        // process OWL paths
        String path = "test:ExampleProp1*";
        PathExpression pe = new PathExpression(reasoner);
        Set<OWLClass> results = pe.processPath(path,subjects);

        System.err.println("results = "+results);
        for(OWLClass result : results){
            for(OWLAnnotation a : EntitySearcher.getAnnotations(result, ontology, factory.getRDFSLabel()).collect(Collectors.toSet())) {
                OWLAnnotationValue val = a.getValue();
                if(val instanceof OWLLiteral) {
                    System.out.println(result + " rdfs:label = " + ((OWLLiteral) val).getLiteral());
                }
            }
        }

    }
}