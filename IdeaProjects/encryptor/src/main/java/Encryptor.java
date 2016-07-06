
import java.io.*;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Encryptor class used to encrypt input files using Caesar Algorithm.
 */
public class Encryptor extends FileModifier {
    private Random randomizer = new Random();
    /**
     * get a file and encrypt it using "Caesar Algorithm".
     * @param inputFile file to encrypt
     */
    public void encrypt(File inputFile) {
        // initialize a byte array to contain encrypted bytes
        int fileLength = (int)inputFile.length();
        byte[] encryptedBytes = new byte[fileLength];
        // randomize a key between 0 and 127 (1 byte) and print it
        byte key = (byte)randomizer.nextInt(Byte.MAX_VALUE + 1);
        System.out.println(strings.getString("keyMsg") + " " + (int)key);
        // read the file and encrypt it byte by byte.
        readBytesFromFile(encryptedBytes, inputFile);
        for (int i = 0; i < fileLength; i++) {
            if (encryptedBytes[i] + key > (int)Byte.MAX_VALUE) {
                encryptedBytes[i] = (byte)(encryptedBytes[i] + key
                        - Byte.MAX_VALUE + Byte.MIN_VALUE - 1);
            } else {
                encryptedBytes[i] = (byte)(encryptedBytes[i] + key);
            }
        }
        // write the encrypted bytes to a new file
        File encryptedFile = new File(inputFile.getPath() + ".encrypted");
        try {
            if (encryptedFile.createNewFile()) {
                writeBytesToFile(encryptedBytes, encryptedFile);
            } else {
                System.out.println(strings.getString("fileReadErrorMsg"));
            }
        } catch (IOException e) {
            System.out.println(strings.getString("unexpectedErrorMsg"));
        }
    }
}
