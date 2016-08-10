package Utilities;

import Algorithms.*;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
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
     * Marshal an Object object into a given XML file using
     * an optional validation schema and an optional custom event handler.
     * @param object object
     * @param file XML file
     * @param classesToBeBound class to bind to the marshaller
     * @throws JAXBException when XML validation fails
     * @throws SAXException when Schema's parsing triggers an error
     */
    public static void marshalObject(Object object, File file, File schemaFile,
                                        ValidationEventHandler handler,
                                        Class[] classesToBeBound)
            throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // validate class with schema file
        if (schemaFile != null) {
            SchemaFactory sf = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);
            marshaller.setSchema(sf.newSchema(schemaFile));
        }
        // set an event handler for the marshaller
        marshaller.setEventHandler(handler);
        marshaller.marshal(object, file);
    }

    /**
     * Marshal an object with no schema validation.
     * @param object object
     * @param file XML file
     * @param classesToBeBound class to bind to the marshaller
     */
    public static void marshalObject(Object object, File file,
                                     Class[] classesToBeBound)
            throws JAXBException, SAXException{
        marshalObject(object, file, null, null, classesToBeBound);
    }

    /**
     * Unmarshal an Object from an XML file using an optional validation schema
     * and an optional custom event handler.
     * @param file XML file
     * @param classesToBeBound class to bind to the unmarshaller
     * @return unmarshalled object from XML file
     * @throws JAXBException when XML validation fails
     * @throws SAXException when Schema's parsing triggers an error
     */
    public static Object unmarshalObject(File file, File schemaFile,
                                            ValidationEventHandler handler,
                                            Class[] classesToBeBound)
            throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        // validate class with schema file
        if (schemaFile != null) {
            SchemaFactory sf = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);
            unmarshaller.setSchema(sf.newSchema(schemaFile));
        }
        // set an event handler for the marshaller
        if (handler != null) {
            unmarshaller.setEventHandler(handler);
        }
        return unmarshaller.unmarshal(file);
    }


    /**
     * Unmarshal an object with no schema validation.
     * @param file XML file
     * @param classesToBeBound class to bind to the unmarshaller
     */
    public static Object unmarshalObject(File file, Class[] classesToBeBound)
            throws JAXBException, SAXException{
        return unmarshalObject(file, null, null, classesToBeBound);
    }
}
