package Structure;

import Algorithms.Algorithm;
import com.google.inject.Inject;

import java.io.File;
import java.util.Scanner;

/**
 * A class that uses the algorithm instance injected to it
 */
public class AlgorithmExecutor {
    private Algorithm algorithm;

    @Inject
    public AlgorithmExecutor(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Encrypt an input file/directory using the injected algorithm
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory
     * @param reader user input reader
     * @return the exact time the encryption process took (in nanoseconds)
     */
    public long encrypt(File inputFile, Scanner reader) {
        return algorithm.encrypt(inputFile, reader);
    }

    /**
     * Decrypt an input file/directory using the injected algorithm
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory.
     * @param reader user input reader
     * @return the exact time the decryption process took (in nanoseconds)
     */
    public long decrypt(File inputFile, Scanner reader) {
        return algorithm.decrypt(inputFile, reader);
    }
}
