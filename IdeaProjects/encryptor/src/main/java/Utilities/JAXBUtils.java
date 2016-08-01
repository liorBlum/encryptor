package Utilities;

import Algorithms.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * A utilities class of JAXB methods
 */
public final class JAXBUtils {
    /**
     * A private constructor to avoid instantiation
     */
    private JAXBUtils() {}
    public static void marshalAlgorithm(Algorithm algorithm, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Algorithm.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(algorithm, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Algorithm unmarshalAlgorithm(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Algorithm.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (Algorithm) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
