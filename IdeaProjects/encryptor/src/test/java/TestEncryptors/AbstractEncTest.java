package TestEncryptors;

import Decryptors.Decryptor;
import Encryptors.Encryptor;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Abstract class for encryption types tests.
 */
public abstract class AbstractEncTest {
    private File exampleFile = new File("example_file.txt");
    private String exampleStr = "Text Example 12345";
    private final PrintStream defOutStream = System.out;
    private final InputStream defInStream = System.in;
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final PrintStream psOut = new PrintStream(baos, true);
    private Encryptor encryptor;
    private Decryptor decryptor;

    /**
     * A constructor for all encryption tests.
     * @param encryptor encryptor to be testes
     * @param decryptor decryptor to be tested
     */
    protected AbstractEncTest(Encryptor encryptor, Decryptor decryptor) {
        this.encryptor = encryptor;
        this.decryptor = decryptor;
    }

    /**
     * create the example file that will be encrypted
     * (if is not already created)
     * @throws IOException
     */
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
     * test encryption and decryption of an example file with
     * any encryption algorithm
     * @throws IOException
     */
    protected void testEncryption() throws IOException {
        createExampleFile();
        // receive the encryption key from System.out
        System.setOut(psOut);
        encryptor.encrypt(exampleFile);
        Byte key = Byte.parseByte(baos.toString().split(":")[1].trim());
        File encryptedFile = new File(exampleFile.getPath() + ".encrypted");
        // send the key to System.in (for the decryptor)
        InputStream isIn = new ByteArrayInputStream(key.toString().getBytes());
        System.setIn(isIn);
        decryptor.decrypt(encryptedFile);
        System.setIn(defInStream);
        System.setOut(defOutStream);
        File decryptedFile = new File(exampleFile.getPath() + "_decrypted"
                + ".encrypted");
        isIn.close();
        psOut.close();
        baos.close();
        // test if encrypted file is different from original file
        assertTrue(encryptedFile.canRead());
        assertFalse(filesAreEqual(exampleFile, encryptedFile));
        // test if decrypted file is equal to the original file
        assertTrue(decryptedFile.canRead());
        assertTrue(filesAreEqual(exampleFile, decryptedFile));
        // delete the encrypted and decrypted files
        encryptedFile.delete();
        decryptedFile.delete();
    }
}
