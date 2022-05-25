import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.RDFS;

import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException {

       Exercise1 ex1 = new Exercise1();
        ex1.run();

        Exercise2 ex2 = new Exercise2();
        ex2.run();

       /* final String NS = "http://example.org/";
        final OntModel model = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM );
        OntProperty p = model.createOntProperty( NS+"PropertyName" );
        p.addDomain( model.createOntResource( NS+"ClassName" ));
        p.addRange( RDFS.Literal );
        p.addLabel("PropertyName", "first");
        model.write( System.out, "RDF/XML-ABBREV" );*/

    }
}
