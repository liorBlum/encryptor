package Logs;

import Logs.FileReport;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Xml report for directory encryption\decryption
 */
@XmlRootElement(name = "directoryReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class DirectoryReport {
    @XmlElement(name = "fileReport")
    @Getter @Setter private List<FileReport> filesReports =
            new ArrayList<FileReport>();

    public void addFileReport(FileReport fileReport) {
        filesReports.add(fileReport);
    }
}
