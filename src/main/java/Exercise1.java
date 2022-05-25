import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.VCARD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Exercise1
{
    public Exercise1()
    {}

    public void run() throws FileNotFoundException {
        String personURI    = "http://www.uni-trier.de/index.php?id=1890";
        String fullName     = "Ralph Bergmann";
        String eMailAddress = "bergmann@uni-trier.de";

        Model model = ModelFactory.createDefaultModel();
        Resource ralphBergmann = model.createResource(personURI);
        ralphBergmann.addProperty(VCARD.FN, fullName);
        ralphBergmann.addProperty(VCARD.EMAIL, eMailAddress);


        System.out.println(ralphBergmann + " " + ralphBergmann.getProperty(VCARD.FN).getObject()
                +" "+ ralphBergmann.getProperty(VCARD.EMAIL).getObject());

        String SOURCE = "http://www.uni-trier.de";
        String NS = SOURCE + "/";

        String subjectName = "SemanticTechnologies";
        String numberOfAssignments = "3";
        String fullNameSeminarLead = "Maximilian Hoffmann";
        String emailAddressSeminarLead = "hoffmannm@uni-trier.de";
        Resource lecture = model.createResource();
        Resource exercise = model.createResource();
        Resource seminarsLead = model.createResource("https://www.uni-trier.de/index.php?id=71847");
        seminarsLead.addProperty(VCARD.NAME, fullNameSeminarLead);
        seminarsLead.addProperty(VCARD.EMAIL, emailAddressSeminarLead);
        Property name = model.createProperty(NS + subjectName);
        Property givesLecture = model.createProperty(NS+"lecture");
        Property givesExercise = model.createProperty(NS+"exercise");
        Property hasExercise = model.createProperty(NS+"hasExercise");
        Property hasAssignments = model.createProperty(NS+"hasAssignments");

        model.add(model.createStatement(lecture, name, subjectName));
        model.add(model.createStatement(exercise, name, subjectName));
        ralphBergmann.addProperty(givesLecture, lecture);
        model.add(model.createStatement(lecture, hasExercise, exercise));
        model.add(model.createStatement(exercise, hasAssignments, numberOfAssignments));
        seminarsLead.addProperty(VCARD.FN, fullNameSeminarLead);
        seminarsLead.addProperty(VCARD.EMAIL, emailAddressSeminarLead);
        model.add(model.createStatement(seminarsLead, givesExercise, exercise));

        RDFWriterI writer = model.getWriter();
        OutputStream out = new FileOutputStream("ex1.rdf");
        writer.write(model,out,"");
    }
}
