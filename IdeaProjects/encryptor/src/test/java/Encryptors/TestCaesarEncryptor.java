package Encryptors;

import Decryptors.CaesarDecryptor;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lior on 07/07/2016.
 */
public class TestCaesarEncryptor extends AbstractEncTest{
    private final PrintStream defOutStream = System.out;
    private final InputStream defInStream = System.in;
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final PrintStream psOut = new PrintStream(baos, true);
    @Test
    public void testCaesarEncryption() throws IOException {
        createExampleFile();
        CaesarEncryptor encryptor = new CaesarEncryptor();
        CaesarDecryptor decryptor = new CaesarDecryptor();
        // receive the encryption key from System.out
        System.setOut(psOut);
        encryptor.encrypt(exampleFile);
        System.setOut(defOutStream);
        Byte key = Byte.parseByte(baos.toString().split(":")[1].trim());
        File encryptedFile = new File(exampleFile.getPath() + ".encrypted");
        // send the key to System.in (for the decryptor)
        InputStream isIn = new ByteArrayInputStream(key.toString().getBytes());
        System.setIn(isIn);
        decryptor.decrypt(encryptedFile);
        System.out.println(key);
        System.setIn(defInStream);
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
