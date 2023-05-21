package org.detwiler.owltools.owlpaths;

import org.detwiler.owltools.owlpaths.PathExpression;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PathParseTest {
    //private WriterPath writerPath = new WriterPath();
    public PathParseTest() {
        //System.out.println(PathParser.parse("(fma:constitutional_part|fma:regional_part)*/fma:attaches_to"));
    }

    /*
    private void visitPath(Path path, boolean needParensThisTime) {
        if ( alwaysInnerParens )
            needParensThisTime = true ;
        boolean b = needParens ;
        needParens = needParensThisTime ;
        path.visit(this) ;
        needParens = b ;
    }
    */


    public static void main(String[] args){
        // load ontology reasoner
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        File ontFile = new File(args[0]);

        // NOTE: the following does note work with Turtle files
        AutoIRIMapper mapper = new AutoIRIMapper(ontFile.getAbsoluteFile().getParentFile(), false);
        manager.getIRIMappers().add(mapper);

        OWLOntology ontology = null;
        try {
            ontology = manager.loadOntologyFromOntologyDocument(ontFile);
        } catch (OWLOntologyCreationException e) {
             e.printStackTrace();
            System.exit(-1);
        }

        // load the ontology to the reasoner
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);

        // the rest is Jena stuff
        //Path path = PathParser.parse("((fma:constitutional_part|fma:regional_part)*/fma:attaches_to)+",pmap);

        // initialize structures
        /*
        PrefixMapping pmap = new PrefixMappingImpl();
        pmap.withDefaultMappings(PrefixMapping.Standard);
        pmap.setNsPrefix("fma", "http://purl.org/sig/ont/fma/");
        */
        //Path path = PathParser.parse("(fma:constitutional_part|fma:regional_part)*/<http://purl.org/sig/ont/fma/attaches_to?sup=http://purl.org/sig/ont/fma/fma10483&inv=http://purl.org/sig/ont/fma/receives_attachment_from>",pmap);

        //Path path = PathParser.parse("(fma:constitutional_part|fma:regional_part)*/fma:attaches_to?inv=http://purl.org/sig/ont/fma/receives_attachment_from>",pmap);

        // set up subjects 
        OWLClass biceps = factory.getOWLClass("http://purl.org/sig/ont/fma/fma37670");
        Set<OWLClass> subjects = new HashSet<>();
        subjects.add(biceps);

        // process OWL paths
        // String path = "(fma:regional_part[SUP=http://purl.org/sig/ont/fma/fma10474])?";
        String path = "(fail:regional_part[SUP=fma:fma10474])?";
        //String path = "(fma:regional_part[INV=fma:regional_part_of])?";
        //String path = "<http://purl.org/sig/ont/fma/constitutional_part>";//"fma:constitutional_part";
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

        //Path path = PathParser.parse("fma:constitutional_part/fma:regional_part/fma:attaches_to",pmap);

        //SerializationContext sContext = new SerializationContext(pmap,true);
        //System.out.println(WriterPath.asString(path));

        //Prologue prologue = new Prologue(pmap);
        //System.out.println(WriterPath.asString(path,prologue));

        //OWLClassPathUtils pathUtils = new OWLClassPathUtils(reasoner);
        //Set<OWLClass> results = pathUtils.process(path,subjects /*,null*/);
        //System.err.println("results = "+results);

        //OWLClassPathUtils

        //System.out.println(path);

        //Path path = PathParser.parse("(fma:constitutional_part|fma:regional_part)*/fma:attaches_to",pmap);
        //path.visit(new PathVisitor());
    }

}
