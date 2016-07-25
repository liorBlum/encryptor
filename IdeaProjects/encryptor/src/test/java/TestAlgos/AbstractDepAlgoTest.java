package TestAlgos;

import Algorithms.Algorithm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Abstract class for dependent algorithms tests.
 */
public abstract class AbstractDepAlgoTest extends AbstractAlgoTest{
    /**
     * A constructor for dependent encryption tests.
     * @param algorithm encryption algorithm to be tested
     * @param name algorithm's name
     */
    protected AbstractDepAlgoTest(Algorithm algorithm, String name) {
        super(algorithm, name);
    }

    /**
     * Get the bytes to send as input to the algorithm.
     * Input may include algorithm codes and key file path.
     * @return input bytes
     */
    protected abstract byte[] getInputToSend();

    @Override
    protected void testAlgorithm() throws IOException {
        System.out.println("Testing " + algoName + "...");
        createExampleFile();
        InputStream isIn = new ByteArrayInputStream(getInputToSend());
        System.setIn(isIn);
        Scanner reader = new Scanner(System.in);
        // encrypt the example file
        long elapsedTime = algorithm.encrypt(exampleFile, reader);
        // test if encryption's time was measured correctly
        assertTrue(elapsedTime > 0);
        File encryptedFile = new File(exampleFile.getPath() + ".encrypted");
        // decrypt the encrypted file (with same algorithm and encryption key
        elapsedTime = algorithm.decrypt(encryptedFile, reader);
        System.setIn(defInStream);
        isIn.close();
        assertTrue(elapsedTime > 0);
        File decryptedFile = new File(exampleFile.getPath() + "_decrypted"
                + ".encrypted");
        // test if encrypted file is different from original file
        assertTrue(encryptedFile.canRead());
        assertFalse(filesAreEqual(exampleFile, encryptedFile));
        // test if decrypted file is equal to the original file
        assertTrue(decryptedFile.canRead());
        assertTrue(filesAreEqual(exampleFile, decryptedFile));
        // delete the encrypted and decrypted files
        encryptedFile.delete();
        decryptedFile.delete();
        System.out.println(algoName + " test completed successfully.\n");
    }
}
