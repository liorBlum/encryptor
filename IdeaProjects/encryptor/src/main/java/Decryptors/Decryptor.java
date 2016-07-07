package Decryptors;

import java.io.File;

/**
 * Interface for encryptors
 */
public interface Decryptor {
    /**
     * Decrypt an input file and write the result in a new file.
     * @param inputFile an input file
     */
    public void decrypt(File inputFile);
}
