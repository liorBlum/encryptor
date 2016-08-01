package Algorithms;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Scanner;


/**
 * Algorithm class used to encrypt/decrypt input files
 * using Double Algorithm.
 */
@XmlRootElement(name="doubleAlgo")
public class DoubleAlgo extends DependentAlgorithm {
    private Algorithm algorithm1 = null;
    private Algorithm algorithm2 = null;

    public Algorithm getAlgorithm1() {
        return algorithm1;
    }

    public Algorithm getAlgorithm2() {
        return algorithm2;
    }

    public void setAlgorithm1(Algorithm algorithm1) {
        this.algorithm1 = algorithm1;
    }

    public void setAlgorithm2(Algorithm algorithm2) {
        this.algorithm2 = algorithm2;
    }

    @Override
    protected void updateAlgorithmMembers(Scanner reader) {
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
