package org.detwiler.owltools.owlpaths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import static org.junit.jupiter.api.TestInstance.*;

@TestInstance(Lifecycle.PER_CLASS)
class PathExpressionTester {

    private OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
    private OWLDataFactory factory = manager.getOWLDataFactory();
    private OWLOntology ontology = null;
    private OWLReasoner reasoner = null;

    private OWLClass start = factory.getOWLClass("http://www.semanticweb.org/detwiler/ontologies/2023/4/owlpathstest#ExampleSource");

    @BeforeAll
    public void initAll(){
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

    @Test
    @DisplayName( "Path with * Test" )
    void processPath() {
        // set up subjects
        Set<OWLClassExpression> subjects = new HashSet<>();
        subjects.add(start);

        // process OWL paths
        String path = "test:ExampleProp1*";
        PathExpression pe = new PathExpression(reasoner);
        Set<OWLClassExpression> results = pe.processPath(path,subjects);

        Set<OWLClassExpression> expectedResults = new HashSet<>();
        expectedResults.add(
                factory.getOWLClass("http://www.semanticweb.org/detwiler/ontologies/2023/4/owlpathstest#ExampleSource")
        );
        expectedResults.add(
                factory.getOWLClass("http://www.semanticweb.org/detwiler/ontologies/2023/4/owlpathstest#ExampleTarget1")
        );
        expectedResults.add(
                factory.getOWLClass("http://www.semanticweb.org/detwiler/ontologies/2023/4/owlpathstest#ExampleTarget2")
        );

        assertEquals(expectedResults,results);
    }
}