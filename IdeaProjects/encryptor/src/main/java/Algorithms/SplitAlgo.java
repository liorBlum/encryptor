package Algorithms;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Scanner;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Split Algorithm.
 */
@XmlRootElement(name="splitAlgo")
public class SplitAlgo extends DependentAlgorithm {
    /**
     * Get and set inner algorithm used by Reverse Algorithm
     * @param algorithm algorithm
     * @return algorithm
     */
    @Getter @Setter private Algorithm algorithm = null;

    /**
     * Algorithm constructor that is used to set the Algorithm's name
     */
    public SplitAlgo() {
        super("Split Algorithm");
    }
    @Override
    protected void updateAlgorithmMembers(Scanner reader) throws IOException {
        if (algorithm == null) {
            System.out.println(strings.getString("splitMsg"));
            showIndepAlgorithmsSelection();
            algorithm = getIndepAlgorithmFromUser(reader);
        }
    }

    @Override
    protected Key generateKey() {
        // generate two random keys
        return new Key(algorithm.generateKey().key,
                algorithm.generateKey().key);
    }

    @Override
    protected Key getDecryptionKey(Key encryptionKey) throws Exception {
        if (encryptionKey.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        // get dual decryption key from the input encryption key
        Key firstKey = new Key(encryptionKey.key);
        Key secondKey = new Key(encryptionKey.secondKey);
        return new Key(algorithm.getDecryptionKey(firstKey).key,
                algorithm.getDecryptionKey(secondKey).key);
    }

    @Override
    protected byte encryptByte(byte b, int idx, Key keyObject)
            throws IOException {
        // split algorithm must receive a dual key
        if (keyObject.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        byte firstKey = keyObject.key;
        byte secondKey = keyObject.secondKey;
        if ((idx & 1) == 1) {
            return algorithm.encryptByte(b, idx, new Key(firstKey));
        } else {
            return algorithm.encryptByte(b, idx, new Key(secondKey));
        }
    }

    @Override
    protected byte decryptByte(byte b, int idx, Key keyObject)
            throws IOException{
        // split algorithm must receive a dual key
        if (keyObject.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        byte firstKey = keyObject.key;
        byte secondKey = keyObject.secondKey;
        if ((idx & 1) == 1) {
            return algorithm.decryptByte(b, idx, new Key(firstKey));
        } else {
            return algorithm.decryptByte(b, idx, new Key(secondKey));
        }
    }
}
