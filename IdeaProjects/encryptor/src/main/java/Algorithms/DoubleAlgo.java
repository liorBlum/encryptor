package Algorithms;
import java.io.IOException;
import java.util.Scanner;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Caesar Algorithm.
 */
public class DoubleAlgo extends Algorithm {
    private Algorithm algorithm1;
    private Algorithm algorithm2;
    public DoubleAlgo(Algorithm algorithm1, Algorithm algorithm2) {
        this.algorithm1 = algorithm1;
        this.algorithm2 = algorithm2;
    }

    @Override
    protected Key generateKey() {
        return new Key(algorithm1.generateKey().key,
                algorithm2.generateKey().key);
    }
    @Override
    protected Key getInputKey(Scanner reader) throws
            IOException {
        // if the key object does not contain two keys, throw an exception
        Key inputKey = super.getInputKey(reader);
        if (inputKey.secondKey != null) {
            return inputKey;
        } else {
            throw new IllegalArgumentException(
                    strings.getString("doubleKeyErrorMsg"));
        }
    }
    /**
     * Encrypt a byte with Double algorithm.
     * @param b input byte
     * @param keyObject encryption key (includes the two keys)
     * @return encrypted byte.
     */
    protected byte encryptByte(byte b, Key keyObject) {
        Key firstKey = new Key(keyObject.key);
        Key secondKey;
        // if only one key is entered, use the same key for both algorithm
        if (keyObject.secondKey == null) {
            secondKey = firstKey;
        } else {
            secondKey = new Key(keyObject.secondKey);
        }
        // encrypt the byte with the first algorithm and then the second one
        return algorithm2.encryptByte(algorithm1.encryptByte(b, firstKey),
                secondKey);
    }

    /**
     * Decrypt a byte with Double algorithm.
     * @param b input byte
     * @param keyObject decryption key (includes the two keys)
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, Key keyObject) {
        Key firstKey = new Key(keyObject.key);
        Key secondKey;
        // if only one key is entered, use the same key for both algorithm
        if (keyObject.secondKey == null) {
            secondKey = firstKey;
        } else {
            secondKey = new Key(keyObject.secondKey);
        }
        // decrypt the byte with the second algorithm and then the first one
        return algorithm1.encryptByte(algorithm2.encryptByte(b, secondKey),
                firstKey);
    }
}
