package Algorithms;

import java.io.IOException;
import java.util.Random;

/**
 * Abstract class for independent algorithms. These algorithms have no
 * algorithm members and therefore act independently.
 */
public abstract class IndependentAlgorithm extends Algorithm {
    protected Random randomizer = new Random();
    /**
     * Algorithm protected constructor that is used to set the Algorithm's name
     * @param name Algorithm's name
     */
    protected IndependentAlgorithm(String name) {
        super(name);
    }

    /*default no-arg constructor for algorithms with no specific name*/
    public IndependentAlgorithm() {}

    /**
     * Generate a random encryption key between -128 and 127
     * @return encryption key
     */
    @Override
    protected Key generateKey() {
        // randomize a key between -128 and 127 (1 byte) and return it
        byte key = (byte)(randomizer.nextInt(2*Byte.MAX_VALUE + 2)
                + Byte.MIN_VALUE);
        return new Key(key);
    }

    /**
     * Compute the decryption key using its respective encryption key.
     * By default, encryption key == decryption key
     * @param encryptionKey encryption key
     * @return decryption key
     * @throws IOException when key is invalid
     */
    @Override
    protected Key getDecryptionKey(Key encryptionKey) throws Exception {
        return encryptionKey;
    }
}
