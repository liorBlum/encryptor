package Structure;

import Algorithms.Algorithm;
import com.google.inject.Inject;
import lombok.Getter;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * A class that uses the algorithm instance injected to it
 */
public class AlgoExecutor {
    @Getter private Algorithm algorithm;

    @Inject
    public AlgoExecutor(Algorithm algorithm) {
        this.algorithm = algorithm;
        // add an observer in order to notify
        // the user when action started\ended
        algorithm.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
    }

    /**
     * Get the full name of the injected algorithm.
     * @return algoriehm's name
     */
    public String getAlgoName() {
        return algorithm.getName();
    }

    /**
     * Encrypt an input file/directory using the injected algorithm
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory
     * @param reader user input reader
     * @return the exact time the encryption process took (in nanoseconds)
     */
    public double encrypt(File inputFile, Scanner reader) {
        return algorithm.encrypt(inputFile, reader);
    }

    /**
     * Decrypt an input file/directory using the injected algorithm
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory.
     * @param reader user input reader
     * @return the exact time the decryption process took (in nanoseconds)
     */
    public double decrypt(File inputFile, Scanner reader) {
        return algorithm.decrypt(inputFile, reader);
    }
}
