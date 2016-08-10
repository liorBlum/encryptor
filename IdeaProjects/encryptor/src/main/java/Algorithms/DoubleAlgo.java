package Algorithms;


import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Scanner;


/**
 * Algorithm class used to encrypt/decrypt input files
 * using Double Algorithm.
 */
@XmlRootElement(name="doubleAlgo")
public class DoubleAlgo extends DependentAlgorithm {
    /**
     * Get and set inner algorithms used by Double Algorithm
     * @param algorithm1 first algorithm
     * @return first algorithm
     */
    @Getter @Setter private IndependentAlgorithm algorithm1 = null;
    /**
     * Get and set inner algorithms used by Double Algorithm
     * @param algorithm1 second algorithm
     * @return second algorithm
     */
    @Getter @Setter private IndependentAlgorithm algorithm2 = null;

    /**
     * Algorithm constructor that is used to set the Algorithm's name
     */
    public DoubleAlgo() {
        super("Double Algorithm");
    }

    @Override
    protected void updateAlgorithmMembers(Scanner reader) throws IOException {
        if ((algorithm1 == null) || (algorithm2 == null)) {
            System.out.println(strings.getString("doubleAlgoMsg"));
            showIndepAlgorithmsSelection();
            System.out.println('\n' + strings.getString("firstAlgoMsg"));
            algorithm1 = getIndepAlgorithmFromUser(reader);
            System.out.println(strings.getString("secondAlgoMsg"));
            algorithm2 = getIndepAlgorithmFromUser(reader);
        }
    }

    @Override
    protected Key generateKey() {
        return new Key(algorithm1.generateKey().key,
                algorithm2.generateKey().key);
    }

    @Override
    protected Key getInputKey(Scanner reader) throws
            Exception {
        // if the key object does not contain two keys, throw an exception
        Key inputKey = super.getInputKey(reader);
        if (inputKey.secondKey != null) {
            return inputKey;
        } else {
            throw new IllegalArgumentException(
                    strings.getString("doubleKeyErrorMsg"));
        }
    }

    @Override
    protected Key getDecryptionKey(Key encryptionKey) throws Exception {
        if (encryptionKey.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        // get dual decryption key from the input encryption key
        Key algo1Key = new Key(encryptionKey.key);
        Key algo2Key = new Key(encryptionKey.secondKey);
        return new Key(algorithm1.getDecryptionKey(algo1Key).key,
                algorithm2.getDecryptionKey(algo2Key).key);
    }

    @Override
    protected byte encryptByte(byte b, int idx, Key keyObject)
            throws IOException {
        // double algorithm must receive a dual key
        if (keyObject.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        Key firstKey = new Key(keyObject.key);
        Key secondKey = new Key(keyObject.secondKey);
        // encrypt the byte with the first algorithm and then the second one
        return algorithm2.encryptByte(
                algorithm1.encryptByte(b, idx, firstKey), idx, secondKey);
    }

    @Override
    protected byte decryptByte(byte b, int idx, Key keyObject)
            throws IOException {
        // double algorithm must receive a dual key
        if (keyObject.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        Key firstKey = new Key(keyObject.key);
        Key secondKey = new Key(keyObject.secondKey);
        // decrypt the byte with the second algorithm and then the first one
        return algorithm1.decryptByte(
                algorithm2.decryptByte(b, idx, secondKey), idx, firstKey);
    }
}
