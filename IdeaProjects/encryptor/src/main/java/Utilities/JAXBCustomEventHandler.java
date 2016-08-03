package Utilities;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import java.util.ResourceBundle;

/**
 * event handler for validation errors in JAXB marshalling/unmarshalling
 */
public class JAXBCustomEventHandler implements ValidationEventHandler {
    private final static ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    /**
     * Handle a JAXB validation event
     * @param e event
     * @return true if the event is not too severe and program
     * can continue. false if event is severe and program should terminate.
     */
    public boolean handleEvent(ValidationEvent e) {
        System.out.println(strings.getString("JAXBError"));
        System.out.println("Message: " + e.getMessage());
        if (e.getLinkedException() != null) {
            System.out.println("Exception: " + e.getLinkedException());
        }
        if (e.getSeverity() == ValidationEvent.ERROR ||
                e.getSeverity() == ValidationEvent.FATAL_ERROR) {
            return false;
        }
        return true;
    }
}
