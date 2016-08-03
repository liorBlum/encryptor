package Utilities;

import Algorithms.*;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * A utilities class of JAXB methods
 */
public final class JAXBUtils {
    /**
     * A private constructor to avoid instantiation
     */
    private JAXBUtils() {}

    /**
     * Marshal an Algorithm object into a given XML file.
     * @param algorithm Algorithm object
     * @param file XML file
     * @throws JAXBException when XML validation fails
     * @throws SAXException when Schema's parsing triggers an error
     */
    public static void marshalAlgorithm(Algorithm algorithm, File file)
            throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Algorithm.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // validate class with schema file
        SchemaFactory sf = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("schema1.xsd"));
        marshaller.setSchema(schema);
        marshaller.setEventHandler(new JAXBCustomEventHandler());

        marshaller.marshal(algorithm, file);
    }

    /**
     * Unmarshal an XML file into an Algorithm object.
     * @param file XML file
     * @throws JAXBException when XML validation fails
     * @throws SAXException when Schema's parsing triggers an error
     */
    public static Algorithm unmarshalAlgorithm(File file)
            throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Algorithm.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        // validate XML file with schema file
        SchemaFactory sf = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("schema1.xsd"));
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new JAXBCustomEventHandler());

        return (Algorithm) unmarshaller.unmarshal(file);
    }
}
