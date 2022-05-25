import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFWriterI;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Calendar;

public class Exercise2
{
    public Exercise2()
    {}

    public void run() throws FileNotFoundException {
        OntModel model = ModelFactory.createOntologyModel();

        String SOURCE = "http://example.org";
        String NS = SOURCE + "#";
        //model.read( SOURCE, "RDF/XML" );

        // creating factory class and its properties and related
        OntClass factory = model.createClass(NS+"factory");
        OntClass country = model.createClass(NS+"country");
        ObjectProperty isLocatedIn = model.createObjectProperty(NS+"countryName");
        DatatypeProperty countryName = model.createDatatypeProperty(NS+"manager");
        DatatypeProperty numberOfEmployees = model.createDatatypeProperty(NS+"employees");
        DatatypeProperty founded = model.createDatatypeProperty(NS+"foundedAt");
        DatatypeProperty manager = model.createDatatypeProperty(NS+"manager");
        isLocatedIn.addDomain(factory);
        isLocatedIn.addRange(country);
        numberOfEmployees.addRange(XSD.integer);
        numberOfEmployees.addDomain(factory);
        founded.addDomain(factory);
        founded.addRange(XSD.gYear);
        manager.addDomain(factory);
        manager.addRange(XSD.Name);
        // creating process class and its properties and related
        OntClass stages = model.createClass(NS+"stages");
        OntClass firstStage = model.createClass(NS+"firstStage");
        OntClass secondStage = model.createClass(NS+"secondStage");
        OntClass thirdStage = model.createClass(NS+"thirdStage");
        OntClass forthStage = model.createClass(NS+"forthStage");
        stages.addSubClass(firstStage);
        stages.addSubClass(secondStage);
        stages.addSubClass(thirdStage);
        stages.addSubClass(forthStage);
        DatatypeProperty costs = model.createDatatypeProperty(NS+"costs");
        DatatypeProperty duration = model.createDatatypeProperty(NS+"duration");
        costs.addDomain(stages);
        costs.addRange(XSD.integer);
        duration.addDomain(stages);
        duration.addRange(XSD.integer);
        ObjectProperty hasProductionStage = model.createObjectProperty(NS + "prodStage");
        hasProductionStage.addDomain(factory);
        hasProductionStage.addRange(stages);
        //process description
        OntClass process = model.createClass(NS+"process");
        process.addSubClass(firstStage);
        process.addSubClass(secondStage);
        process.addSubClass(thirdStage);
        process.addSubClass(forthStage);
        DatatypeProperty startTime = model.createDatatypeProperty(NS+"startTime");
        DatatypeProperty endTime = model.createDatatypeProperty(NS+"endTime");
        DatatypeProperty productReference = model.createDatatypeProperty(NS+"productReference");
        startTime.addDomain(process);
        startTime.addRange(XSD.dateTime);
        endTime.addDomain(process);
        endTime.addRange(XSD.dateTime);
        productReference.addDomain(process);
        productReference.addRange(XSD.normalizedString);
        ObjectProperty factoryHandlingProcess = model.createObjectProperty(NS + "factory");
        factoryHandlingProcess.addDomain(process);
        factoryHandlingProcess.addRange(factory);
        //basis
        Individual poland = model.createIndividual(NS+"poland", country);
        Individual india = model.createIndividual(NS+"india", country);
        Individual spain = model.createIndividual(NS+"spain", country);

        Individual factory1 = model.createIndividual(NS+"factory1", factory);
        Individual factory2 = model.createIndividual(NS+"factory2", factory);
        Individual factory3 = model.createIndividual(NS+"factory3", factory);

        factory1.addProperty(isLocatedIn, poland);
        factory1.addLiteral(numberOfEmployees, 3000);
        factory1.addLiteral(founded, 1995);
        factory1.addLiteral(manager, "VasilisaIvanovchak");

        factory2.addProperty(isLocatedIn, india);
        factory2.addLiteral(numberOfEmployees, 4000);
        factory2.addLiteral(founded, 2003);
        factory2.addLiteral(manager, "AsmeeDashmir");

        factory3.addProperty(isLocatedIn, spain);
        factory3.addLiteral(numberOfEmployees, 2500);
        factory3.addLiteral(founded, 1990);
        factory3.addLiteral(manager, "PedroIglesias");

        firstStage.addLiteral(costs, 100);
        secondStage.addLiteral(costs, 300);
        thirdStage.addLiteral(costs, 200);
        forthStage.addLiteral(costs, 400);

        firstStage.addLiteral(duration, 2);
        secondStage.addLiteral(duration, 3);
        thirdStage.addLiteral(duration, 3);
        forthStage.addLiteral(duration, 4);

        factory1.addProperty(hasProductionStage, firstStage);
        factory1.addProperty(hasProductionStage, forthStage);
        factory2.addProperty(hasProductionStage, firstStage);
        factory2.addProperty(hasProductionStage, thirdStage);
        factory2.addProperty(hasProductionStage, forthStage);
        factory3.addProperty(hasProductionStage, secondStage);
        factory3.addProperty(hasProductionStage, thirdStage);
        factory3.addProperty(hasProductionStage, forthStage);

        String str="2022-05-19 00:00:00";
        Timestamp timestamp = Timestamp.valueOf(str);
        //Poland-Spain-India
        Individual process1 = model.createIndividual(NS+"process1", process);
        process1.addLiteral(productReference, "AbayomiIalvin2022-04-29");
        process1.addLiteral(startTime, timestamp);
        Individual firstStage1 = model.createIndividual(NS+"firstStage1", firstStage);
        firstStage1.addProperty(factoryHandlingProcess, factory1);
        firstStage1.addLiteral(startTime, timestamp);
        timestamp = addDays(timestamp,2);
        firstStage1.addLiteral(endTime, timestamp);
        Individual secondStage1 = model.createIndividual(NS+"secondStage1", secondStage);
        secondStage1.addProperty(factoryHandlingProcess, factory3);
        secondStage1.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        secondStage1.addLiteral(endTime, timestamp);
        Individual thirdStage1 = model.createIndividual(NS+"thirdStage1", thirdStage);
        thirdStage1.addProperty(factoryHandlingProcess, factory2);
        thirdStage1.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        thirdStage1.addLiteral(endTime, timestamp);
        Individual forthStage1 = model.createIndividual(NS+"forthStage1", forthStage);
        forthStage1.addProperty(factoryHandlingProcess, factory2);
        forthStage1.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,4);
        forthStage1.addLiteral(endTime, timestamp);
        process1.addLiteral(endTime, timestamp);
        //Poland-India-Poland
        timestamp = Timestamp.valueOf(str);
        Individual process2 = model.createIndividual(NS+"process2", process);
        process2.addLiteral(productReference, "ArisuMarioki2022-05-01");
        process2.addLiteral(startTime, timestamp);
        Individual firstStage2 = model.createIndividual(NS+"firstStage2", firstStage);
        firstStage2.addProperty(factoryHandlingProcess, factory1);
        firstStage2.addLiteral(startTime, timestamp);
        timestamp = addDays(timestamp,2);
        firstStage2.addLiteral(endTime, timestamp);
        Individual secondStage2 = model.createIndividual(NS+"secondStage2", secondStage);
        secondStage2.addProperty(factoryHandlingProcess, factory2);
        secondStage2.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        secondStage1.addLiteral(endTime, timestamp);
        Individual thirdStage2 = model.createIndividual(NS+"thirdStage2", thirdStage);
        thirdStage2.addProperty(factoryHandlingProcess, factory2);
        thirdStage2.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        thirdStage2.addLiteral(endTime, timestamp);
        Individual forthStage2 = model.createIndividual(NS+"forthStage2", forthStage);
        forthStage2.addProperty(factoryHandlingProcess, factory1);
        forthStage2.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,4);
        forthStage2.addLiteral(endTime, timestamp);
        process2.addLiteral(endTime, timestamp);
        //India-Spain
        timestamp = Timestamp.valueOf(str);
        Individual process3 = model.createIndividual(NS+"process3", process);
        process3.addLiteral(productReference, "LenaSchmidt2022-04-30");
        process3.addLiteral(startTime, timestamp);
        Individual firstStage3 = model.createIndividual(NS+"firstStage3", firstStage);
        firstStage3.addProperty(factoryHandlingProcess, factory2);
        firstStage3.addLiteral(startTime, timestamp);
        timestamp = addDays(timestamp,2);
        firstStage3.addLiteral(endTime, timestamp);
        Individual secondStage3 = model.createIndividual(NS+"secondStage3", secondStage);
        secondStage3.addProperty(factoryHandlingProcess, factory3);
        secondStage3.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        secondStage3.addLiteral(endTime, timestamp);
        Individual thirdStage3 = model.createIndividual(NS+"thirdStage3", thirdStage);
        thirdStage3.addProperty(factoryHandlingProcess, factory3);
        thirdStage3.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        thirdStage3.addLiteral(endTime, timestamp);
        Individual forthStage3 = model.createIndividual(NS+"forthStage3", forthStage);
        forthStage3.addProperty(factoryHandlingProcess, factory3);
        forthStage3.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,4);
        forthStage3.addLiteral(endTime, timestamp);
        process3.addLiteral(endTime, timestamp);
        //Poland-Spain
        timestamp = Timestamp.valueOf(str);
        Individual process4 = model.createIndividual(NS+"process4", process);
        process4.addLiteral(productReference, "PabloNyandu2022-05-02");
        process4.addLiteral(startTime, timestamp);
        Individual firstStage4 = model.createIndividual(NS+"firstStage4", firstStage);
        firstStage4.addProperty(factoryHandlingProcess, factory1);
        firstStage4.addLiteral(startTime, timestamp);
        timestamp = addDays(timestamp,2);
        firstStage4.addLiteral(endTime, timestamp);
        Individual secondStage4 = model.createIndividual(NS+"secondStage4", secondStage);
        secondStage4.addProperty(factoryHandlingProcess, factory3);
        secondStage4.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        secondStage4.addLiteral(endTime, timestamp);
        Individual thirdStage4 = model.createIndividual(NS+"thirdStage4", thirdStage);
        thirdStage4.addProperty(factoryHandlingProcess, factory3);
        thirdStage4.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        thirdStage4.addLiteral(endTime, timestamp);
        Individual forthStage4 = model.createIndividual(NS+"forthStage4", forthStage);
        forthStage4.addProperty(factoryHandlingProcess, factory3);
        forthStage4.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,4);
        forthStage4.addLiteral(endTime, timestamp);
        process4.addLiteral(endTime, timestamp);
        //India-Spain-India
        timestamp = Timestamp.valueOf(str);
        Individual process5 = model.createIndividual(NS+"process5", process);
        process5.addLiteral(productReference, "QuarbanAkhtar2022-05-12");
        process5.addLiteral(startTime, timestamp);
        Individual firstStage5 = model.createIndividual(NS+"firstStage5", firstStage);
        firstStage5.addProperty(factoryHandlingProcess, factory2);
        firstStage5.addLiteral(startTime, timestamp);
        timestamp = addDays(timestamp,2);
        firstStage5.addLiteral(endTime, timestamp);
        Individual secondStage5 = model.createIndividual(NS+"secondStage5", secondStage);
        secondStage5.addProperty(factoryHandlingProcess, factory3);
        secondStage5.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        secondStage5.addLiteral(endTime, timestamp);
        Individual thirdStage5 = model.createIndividual(NS+"thirdStage5", thirdStage);
        thirdStage5.addProperty(factoryHandlingProcess, factory2);
        thirdStage5.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,3);
        thirdStage5.addLiteral(endTime, timestamp);
        Individual forthStage5 = model.createIndividual(NS+"forthStage5", forthStage);
        forthStage5.addProperty(factoryHandlingProcess, factory2);
        forthStage5.addLiteral(startTime,timestamp);
        timestamp = addDays(timestamp,4);
        forthStage5.addLiteral(endTime, timestamp);
        process5.addLiteral(endTime, timestamp);

        model.write( System.out, "RDF/XML-ABBREV" );

        /*RDFWriterI writer = model.getWriter();
        OutputStream out = new FileOutputStream("ex2.rdf");
        writer.write(model,out,"");*/

    }

    public static Timestamp addDays(Timestamp date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return new Timestamp(cal.getTime().getTime());

    }
}
