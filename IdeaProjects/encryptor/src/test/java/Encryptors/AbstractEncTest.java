package Encryptors;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Abstract class for encryption types tests.
 */
public abstract class AbstractEncTest {
    File exampleFile = new File("example_file_caesar.txt");
    String exampleStr = "Text Example 12345";

    protected void createExampleFile() throws IOException{
        if (exampleFile.createNewFile()) {
            FileWriter fw = new FileWriter(exampleFile);
            fw.write(exampleStr);
            fw.close();
        }
    }
    /**
     * check whether two files are equal (in content)
     * @param f1 first file
     * @param f2 second file
     * @return true if equal. Otherwise, false.
     */
    protected boolean filesAreEqual(File f1, File f2) throws IOException {
        int filesLength = (int)f1.length();
        // first of all, check the files' lengths
        if (f2.length() != filesLength) {
            return false;
        }
        // compare the two files' contents
        FileReader fr1 = new FileReader(f1);
        FileReader fr2 = new FileReader(f2);
        char[] f1Content = new char[filesLength];
        char[] f2Content = new char[filesLength];
        fr1.read(f1Content);
        fr1.close();
        fr2.read(f2Content);
        fr2.close();
        return Arrays.equals(f1Content, f2Content);
    }
}
