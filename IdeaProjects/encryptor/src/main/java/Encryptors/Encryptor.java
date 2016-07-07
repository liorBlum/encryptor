package Encryptors;

import java.io.File;

/**
 * Interface for encryptors
 */
public interface Encryptor {
    /**
     * Encrypt an input file and write the result in a new file.
     * @param inputFile an input file
     */
    public void encrypt(File inputFile);
}
