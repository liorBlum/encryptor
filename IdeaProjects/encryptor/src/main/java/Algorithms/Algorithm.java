package Algorithms;

import Utilities.FileModifierUtils;
import Utilities.SerializationUtils;
import Utilities.UserInputUtils;

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
    protected Random randomizer = new Random();

    /**
     * Get the file path of the encryption key from the user input
     * @param reader System.in scanner
     * @return the decryption key
     * @throws IOException when I/O error occurs
     */
    protected Key getInputKey(Scanner reader) throws
            Exception {
        System.out.println(strings.getString("keyInputMsg"));
        return ((Key)SerializationUtils.deserializeObject(
                UserInputUtils.getInputFile(reader)));
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
     * Compute the decryption key using its respective encryption key.
     * By default, encryption key == decryption key
     * @param encryptionKey encryption key
     * @return decryption key
     * @throws IOException when key is invalid
     */
    protected Key getDecryptionKey(Key encryptionKey) throws Exception {
        return encryptionKey;
    }

    /**
     * Encrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param idx byte's index in file
     *@param key encryption key  @return encrypted byte.
     * @throws IOException when key is invalid
     */
    protected abstract byte encryptByte(byte b, int idx, Key key)
            throws IOException;

    /**
     * Decrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param idx byte's index in file
     * @param key decryption key
     * @return decrypted byte.
     * @throws IOException when key is invalid
     */
    protected abstract byte decryptByte(byte b, int idx, Key key)
            throws IOException;

    /**
     * Encrypt/Decrypt a single normal file (not directory)
     * @param file source file
     * @param key key
     * @param destinationFilePath filepath of the outcome
     * @param actionCode "e" for encryption / "d" for decryption
     * @throws IOException when I/O error occurs
     * @throws IllegalArgumentException when unexpected error occurs
     */
    protected void execAlgoOnFile(File file, Key key, String destinationFilePath,
                                  String actionCode) throws Exception {
        // initialize a byte array to contain processed bytes
        int fileLength = (int)file.length();
        byte[] processedBytes = new byte[fileLength];
        // read the file and process it byte-by-byte.
        FileModifierUtils.readBytesFromFile(processedBytes, file);
        if (actionCode.equals(strings.getString("encOption"))) {
            for (int i = 0; i < fileLength; i++) {
                processedBytes[i] = encryptByte(processedBytes[i], i, key);
            }
        } else if (actionCode.equals(strings.getString("decOption"))) {
            for (int i = 0; i < fileLength; i++) {
                processedBytes[i] = decryptByte(processedBytes[i], i, key);
            }
        } else {
            throw new IllegalArgumentException();
        }
        // write the processed bytes to a new file
        File processedFile =
                FileModifierUtils.createFileInPath(destinationFilePath);
        FileModifierUtils.writeBytesToFile(processedBytes, processedFile);
    }

    /**
     * Encrypt/Decrypt a directory
     * @param directory directory
     * @param key key
     * @param processedDirectory directory that will include
     *                           processed files (encrypted/decrypted)
     * @param actionCode "e" for encryption / "d" for decryption
     * @throws Exception when invalid input is entered/ unexpected error
     */
    protected void execAlgoOnDirectory(File directory, Key key,
                                       File processedDirectory,
                                       String actionCode) throws Exception {
        // source/outcome directories must both be directories
        if (!directory.isDirectory() || !processedDirectory.isDirectory()) {
            throw new IllegalArgumentException(
                    strings.getString("unexpectedErrorMsg"));
        }
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalArgumentException(
                    strings.getString("unexpectedErrorMsg"));
        }
        // process the directory file-by-file
        for (File file : files) {
            if (file.canRead() && file.isFile()) {
                execAlgoOnFile(file, key,
                        processedDirectory.getPath() + "/" + file.getName(),
                        actionCode);
            }
        }
    }
    /**
     * Encrypt an input file/directory
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory
     * @return the exact time the encryption process began
     */
    public long encrypt(File inputFile, Scanner reader) {
        try {
            // Generate a random encryption key
            Key key = generateKey();
            File keyFile;
            System.out.println(strings.getString("keyMsg"));
            // notify observers that encryption started and measure time
            setChanged();
            notifyObservers(strings.getString("encStartMsg"));
            long startTime = System.nanoTime();
            // start encrypting file/directory
            if (inputFile.isFile()) {
                execAlgoOnFile(inputFile, key, inputFile.getPath()
                        + ".encrypted", strings.getString("encOption"));
                // the key is stored in the same directory
                keyFile = new File(inputFile.getParent(),
                        strings.getString("keyFileName"));
            } else if (inputFile.isDirectory()) {
                // create encrypted sub-directory and encrypt input directory
                File encryptedDirectory = new File(inputFile, "encrypted");
                encryptedDirectory.mkdir();
                execAlgoOnDirectory(inputFile, key, encryptedDirectory,
                        strings.getString("encOption"));
                // the key is stored in 'encrypted' directory
                keyFile = new File(inputFile,
                        strings.getString("keyFileName"));
            } else {
                System.out.println(strings.getString("unexpectedErrorMsg"));
                return 0;
            }
            // serialize the kay
            SerializationUtils.serializeObject(keyFile, key);
            setChanged();
            notifyObservers(strings.getString("encEndMsg"));
            // return elapsed time after encryption ends
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
     * Decrypt an input file/directory
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory.
     * @return the exact time the decryption process took
     */
    public long decrypt(File inputFile, Scanner reader) {
        Key key;
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
            // make the name of the decrypted file
            int fileExtIdx = inputFile.getPath().lastIndexOf('.');
            if (fileExtIdx == -1) {
                fileExtIdx = inputFile.getPath().length();
            }
            String fileName = inputFile.getPath().substring(0, fileExtIdx);
            String fileExtension = inputFile.getPath().substring(fileExtIdx);
            // notify observers that decryption started and measure time
            setChanged();
            notifyObservers(strings.getString("decStartMsg"));
            long startTime = System.nanoTime();
            // start decrypting the file/directory
            if (inputFile.isFile()) {
                execAlgoOnFile(inputFile, key, fileName + "_decrypted"
                        + fileExtension, strings.getString("decOption"));
            } else if (inputFile.isDirectory()) {
                // create decrypted sub-directory and decrypt input directory
                File decryptedDirectory = new File(inputFile, "decrypted");
                decryptedDirectory.mkdir();
                execAlgoOnDirectory(inputFile, key, decryptedDirectory,
                        strings.getString("decOption"));
            } else {
                System.out.println(strings.getString("unexpectedErrorMsg"));
                return 0;
            }
            // notify observers that decryption ended and return elapsed time
            setChanged();
            notifyObservers(strings.getString("decEndMsg"));
            return System.nanoTime() - startTime;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (Exception e) {
            System.out.println(strings.getString("unexpectedErrorMsg"));
            return 0;
        }
    }
}
