package Algorithms;
import Structure.Menu;
import Utilities.UserInputUtils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Caesar Algorithm.
 */
public class DoubleAlgo extends Algorithm {
    private Algorithm algorithm1;
    private Algorithm algorithm2;

    /**
     * Display all independent algorithms found in properties
     */
    private void showIndepAlgorithmsSelection() {
        Enumeration<String> indepAlgorithms = independentAlgosCodes.getKeys();
        // go over all algorithms and print them as choices
        while (indepAlgorithms.hasMoreElements()) {
            String algo = indepAlgorithms.nextElement();
            System.out.println("For " + algo + " Enter: "
                    + independentAlgosCodes.getString(algo));
        }
    }

    /**
     * Get valid algorithm from the user input. The algorithm
     * must be 'independent algorithm', an algorithm which does not
     * depend on any other algorithm (in order to avoid infinite loops
     * of algorithms calls
     * @return independent algorithm (object)
     */
    private Algorithm getIndepAlgorithmFromUser() {
        while (true) {
            try {
                String algoCode = UserInputUtils.getValidUserInput(
                        Menu.indepAlgosMap.keySet());
                return Menu.indepAlgosMap.get(algoCode);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Update Algorithm member(s) of this dependent algorithm
     */
    public void updateAlgorithmMembers() {
        System.out.println(strings.getString("doubleAlgoMsg"));
        showIndepAlgorithmsSelection();
        System.out.println('\n' + strings.getString("firstAlgoMsg"));
        algorithm1 = getIndepAlgorithmFromUser();
        System.out.println(strings.getString("secondAlgoMsg"));
        algorithm2 = getIndepAlgorithmFromUser();
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

    @Override
    protected Key getDecryptionKey(Key encryptionKey) throws IOException {
        if (encryptionKey.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        Key algo1Key = new Key(encryptionKey.key);
        Key algo2Key = new Key(encryptionKey.secondKey);
        return new Key(algorithm1.getDecryptionKey(algo1Key).key,
                        algorithm2.getDecryptionKey(algo2Key).key);
    }

    /**
     * Encrypt a byte with Double algorithm.
     * @param b input byte
     * @param keyObject encryption key (includes the two keys)
     * @return encrypted byte.
     * @throws IOException when key is not dual
     */
    protected byte encryptByte(byte b, Key keyObject) throws IOException {
        if (keyObject.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        Key firstKey = new Key(keyObject.key);
        Key secondKey = new Key(keyObject.secondKey);
        // encrypt the byte with the first algorithm and then the second one
        return algorithm2.encryptByte(algorithm1.encryptByte(b, firstKey),
                secondKey);
    }

    /**
     * Decrypt a byte with Double algorithm.
     * @param b input byte
     * @param keyObject decryption key (includes the two keys)
     * @return decrypted byte.
     * @throws IOException when key is not dual
     */
    protected byte decryptByte(byte b, Key keyObject) throws IOException {
        if (keyObject.secondKey == null) {
            throw new IOException(strings.getString("doubleKeyErrorMsg"));
        }
        Key firstKey = new Key(keyObject.key);
        Key secondKey = new Key(keyObject.secondKey);
        // decrypt the byte with the second algorithm and then the first one
        return algorithm1.decryptByte(algorithm2.decryptByte(b, secondKey),
                firstKey);
    }

    @Override
    public long encrypt(File inputFile) {
        updateAlgorithmMembers();
        return super.encrypt(inputFile);
    }

    @Override
    public long decrypt(File inputFile) {
        updateAlgorithmMembers();
        return super.decrypt(inputFile);
    }
}
