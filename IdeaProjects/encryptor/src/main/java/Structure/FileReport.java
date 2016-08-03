package Structure;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

/**
 * Xml report for file encryption\decryption
 */
@XmlRootElement(name = "fileReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileReport {
    @XmlID @XmlAttribute(name = "name")
    @Getter @Setter private String fileName;
    @Getter @Setter private boolean succeded;
    @Getter @Setter private long time;
    @Getter @Setter private String exceptionName;
    @Getter @Setter private String exceptionMessage;
    @Getter @Setter private String exceptionStacktrace;
}
