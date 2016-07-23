package Algorithms;

import Utilities.FileModifierUtils;
import Utilities.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Abstract class for encryptors
 */
public abstract class Algorithm extends Observable {
    protected final static ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    protected final static ResourceBundle independentAlgosCodes =
            ResourceBundle.getBundle("indep_algorithms");
    protected Random randomizer = new Random();

    /**
     * Get the file path of the encryption key from the user input
     * @return the decryption key
     * @throws IOException when I/O error occurs
     */
    protected Key getInputKey(Scanner reader) throws
            IOException {
        String input;
        System.out.println(strings.getString("keyInputMsg"));
        input = reader.nextLine();
        return ((Key)SerializationUtils.deserializeObject(new File(input)));
    }

    /**
     * Compute the decryption key using its respective encryption key.
     * By default, encryption key == decryption key
     * @param encryptionKey encryption key
     * @return decryption key
     * @throws IOException when key is invalid
     */
    protected Key getDecryptionKey(Key encryptionKey) throws IOException {
        return encryptionKey;
    }
    /**
     * Generate a random encryption key between -128 and 127
     * @return encryption key
     */
    protected Key generateKey() {
        // randomize a key between -128 and 127 (1 byte) and return it
        byte key = (byte)(randomizer.nextInt(2*Byte.MAX_VALUE + 2)
                + Byte.MIN_VALUE);
        return new Key(key);
    }

    /**
     * Encrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param key encryption key
     * @return encrypted byte.
     * @throws IOException when key is invalid
     */
    protected abstract byte encryptByte(byte b, Key key) throws IOException;

    /**
     * Decrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param key decryption key
     * @return decrypted byte.
     * @throws IOException when key is invalid
     */
    protected abstract byte decryptByte(byte b, Key key) throws IOException;

    /**
     * Encrypt an input file and write the result in a new file.
     * @param inputFile an input file
     * @return the exact time the encryption process began
     */
    public long encrypt(File inputFile) {
        // initialize a byte array to contain encrypted bytes
        int fileLength = (int)inputFile.length();
        byte[] encryptedBytes = new byte[fileLength];
        try {
            // Generate a random encryption key and serialize it.
             // (save it as "key.bin"
            Key key = generateKey();
            File keyFile = new File(inputFile.getParent(),
                    strings.getString("keyFileName"));
            SerializationUtils.serializeObject(keyFile, key);
            System.out.println(strings.getString("keyMsg"));
            // notify observers that encryption started and measure time
            setChanged();
            notifyObservers(strings.getString("encStartMsg"));
            long startTime = System.nanoTime();
            // read the file and encrypt it byte by byte.
            FileModifierUtils.readBytesFromFile(encryptedBytes, inputFile);
            for (int i = 0; i < fileLength; i++) {
                encryptedBytes[i] = encryptByte(encryptedBytes[i], key);
            }
             // write the encrypted bytes to a new file
            File encryptedFile = FileModifierUtils.createFileInPath(
                    inputFile.getPath() + ".encrypted");
            FileModifierUtils.writeBytesToFile(encryptedBytes, encryptedFile);
            // notify observers that encryption ended and return elapsed time
            setChanged();
            notifyObservers(strings.getString("encEndMsg"));
            return System.nanoTime() - startTime;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (Exception e) {
            System.out.println(strings.getString("unexpectedErrorMsg"));
            return 0;
        }
    }

    /**
     * Decrypt an input file and write the result in a new file.
     * @param inputFile an input file.
     * @return the exact time the decryption process took
     */
    public long decrypt(File inputFile) {
        Scanner reader = new Scanner(System.in);
        Key key;
        // initialize a byte array to contain decrypted bytes
        int fileLength = (int) inputFile.length();
        byte[] decryptedBytes = new byte[fileLength];
        // get a decryption key from the user
        while (true) {
            try {
                key = getDecryptionKey(getInputKey(reader));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            // notify observers that decryption started and measure time
            setChanged();
            notifyObservers(strings.getString("decStartMsg"));
            long startTime = System.nanoTime();
            // read the file and decrypt it byte by byte.
            FileModifierUtils.readBytesFromFile(decryptedBytes, inputFile);
            for (int i = 0; i < fileLength; i++) {
                decryptedBytes[i] = decryptByte(decryptedBytes[i], key);
            }
            // write the decrypted bytes to a new file
            int fileExtIdx = inputFile.getPath().lastIndexOf('.');
            if (fileExtIdx == -1) {
                fileExtIdx = inputFile.getPath().length();
            }
            String fileName = inputFile.getPath().substring(0, fileExtIdx);
            String fileExtension = inputFile.getPath().substring(fileExtIdx);
            File decryptedFile = FileModifierUtils.createFileInPath(fileName
                    + "_decrypted" + fileExtension);
            FileModifierUtils.writeBytesToFile(decryptedBytes, decryptedFile);
            // notify observers that decryption ended
            setChanged();
            notifyObservers(strings.getString("decEndMsg"));
            return System.nanoTime() - startTime;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
