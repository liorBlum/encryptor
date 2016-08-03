package Structure;


import Algorithms.DoubleAlgo;
import Utilities.JAXBUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Lior on 21/05/2016.
 */
public class Main {
    public static void main(String[] args) throws JAXBException, SAXException{
        /*DirectoryReport dr = new DirectoryReport();
        FileReport fr1 = new FileReport();
        fr1.setFileName("a1.txt");
        fr1.setSucceded(true);
        fr1.setTime(16);
        fr1.setExceptionName(null);
        fr1.setExceptionMessage(null);
        fr1.setExceptionStacktrace(null);

        dr.addFileReport(fr1);

        JAXBContext jaxbContext = JAXBContext.newInstance(DirectoryReport.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(dr, new File("directoryReport.xml"));*/

        Menu menu = Menu.getInstance();
        menu.showMenu();
    }
}
