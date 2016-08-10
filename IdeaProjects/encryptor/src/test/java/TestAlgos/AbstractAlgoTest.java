package TestAlgos;

import Algorithms.Algorithm;
import java.io.*;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Abstract class for encryption algorithms tests.
 */
public abstract class AbstractAlgoTest {
    protected File exampleFolder = new File("exampleFolder");
    protected File exampleFile1 = new File(exampleFolder, "example_file1.txt");
    protected File exampleFile2 = new File(exampleFolder, "example_file2.txt");
    protected final InputStream defInStream = System.in;
    protected final ResourceBundle strings = ResourceBundle.getBundle("strings");
    protected Algorithm algorithm;
    protected String algoName;
    protected final String ls = System.getProperty("line.separator");
    protected final ResourceBundle independentAlgosCodes =
            ResourceBundle.getBundle("indep_algorithms");

    /**
     * A constructor for all encryption tests.
     * @param algorithm encryption algorithm to be tested
     * @param name algorithm's name
     */
    protected AbstractAlgoTest(Algorithm algorithm, String name) {
        this.algoName = name;
        this.algorithm = algorithm;
        algorithm.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
    }

    /**
     * create the example directory that will be encrypted
     * (if is not already created)
     * @throws IOException
     */
    protected void createExampleFiles() throws IOException{
        String exampleStr1 = "Text Example 12345";
        String exampleStr2 = "abcdefgh *&^%$##?>";
        if (exampleFolder.mkdir()) {
            FileWriter fw1 = new FileWriter(exampleFile1);
            FileWriter fw2 = new FileWriter(exampleFile2);
            fw1.write(exampleStr1);
            fw2.write(exampleStr2);
            fw1.close();
            fw2.close();
        }
    }
    /**
     * check whether two files are equal (in content)
     * @param f1 first file
     * @param f2 second file
     * @return true if equal. Otherwise, false.
     */
    protected boolean filesAreEqual(File f1, File f2) throws IOException {
        if (!f1.isFile() || !f2.isFile()) {
            throw new IOException(strings.getString("inputErrorMsg"));
        }
        int filesLength = (int) f1.length();
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

    /**
     * Get the bytes to send as input to the algorithm.
     * Input may include algorithm codes and key file path.
     * @return input bytes
     */
    protected byte[] getInputToSend() {
        return (exampleFolder.getPath() + "/"
                + strings.getString("keyFileName")).getBytes();
    }

    /**
     * test encryption and decryption of an example file with
     * any encryption algorithm
     * @throws IOException
     */
    protected void testAlgorithm() throws IOException {
        System.out.println("Testing " + algoName + "...");
        createExampleFiles();
        InputStream isIn = new ByteArrayInputStream(getInputToSend());
        System.setIn(isIn);
        Scanner reader = new Scanner(System.in);
        // encrypt the example files
        double elapsedTime = algorithm.encrypt(exampleFolder, reader);
        // test if encryption's time was measured correctly
        assertTrue(elapsedTime > 0);
        File encryptedFolder = new File(exampleFolder, "encrypted");
        File encryptedFile1 = new File(encryptedFolder, exampleFile1.getName());
        File encryptedFile2 = new File(encryptedFolder, exampleFile2.getName());
        // decrypt the encrypted file (with same algorithm and encryption key)
        elapsedTime = algorithm.decrypt(encryptedFolder, reader);
        System.setIn(defInStream);
        isIn.close();
        // delete unnecessary key
        new File(exampleFolder.getPath() + "/"
                + strings.getString("keyFileName")).delete();
        assertTrue(elapsedTime > 0);
        File decryptedFolder = new File(encryptedFolder, "decrypted");
        File decryptedFile1 = new File(decryptedFolder, exampleFile1.getName());
        File decryptedFile2 = new File(decryptedFolder, exampleFile2.getName());
        // test if encrypted files are different from original files
        assertFalse(filesAreEqual(exampleFile1, encryptedFile1));
        assertFalse(filesAreEqual(exampleFile2, encryptedFile2));
        // test if decrypted files are equal to the original files
        assertTrue(filesAreEqual(exampleFile1, decryptedFile1));
        assertTrue(filesAreEqual(exampleFile2, decryptedFile2));
        // delete the encrypted and decrypted files
        encryptedFile1.delete();
        encryptedFile2.delete();
        decryptedFile1.delete();
        decryptedFile2.delete();
        decryptedFolder.delete();
        encryptedFolder.delete();
        System.out.println(algoName + " test completed successfully.\n");
    }
}
