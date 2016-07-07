package Decryptors;
import Structure.FileModifier;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Decryptor class used to decrypt input files using Caesar Algorithm.
 */
public class CaesarDecryptor extends FileModifier implements Decryptor {
    /**
     * Get a decryption key between 0 and 127 from the user input
     * @return the decryption key
     */
    private byte getInputKey() {
        Scanner reader = new Scanner(System.in);
        String input;
        System.out.println(strings.getString("keyInputMsg"));
        while (true) {
            input = reader.nextLine();
            if (Integer.parseInt(input) >= 0
                    && Integer.parseInt(input) <= Byte.MAX_VALUE) {
                return Byte.parseByte(input);
            }
            System.out.println(strings.getString("keyInputErrorMsg"));
        }
    }
    /**
     * get a file and decrypt it using "Caesar Algorithm".
     *
     * @param inputFile file to decrypt
     */
    public void decrypt(File inputFile) {
        // initialize a byte array to contain decrypted bytes
        int fileLength = (int) inputFile.length();
        byte[] decryptedBytes = new byte[fileLength];
        // get a decryption key from the user
        byte key = getInputKey();
        // read the file and decrypt it byte by byte.
        readBytesFromFile(decryptedBytes, inputFile);
        for (int i = 0; i < fileLength; i++) {
            if (decryptedBytes[i] - key < (int) Byte.MIN_VALUE) {
                decryptedBytes[i] = (byte) (decryptedBytes[i] - key
                        + Byte.MAX_VALUE - Byte.MIN_VALUE + 1);
            } else {
                decryptedBytes[i] = (byte) (decryptedBytes[i] - key);
            }
        }
        // write the decrypted bytes to a new file
        int fileExtIdx = inputFile.getPath().lastIndexOf('.');
        String fileName = inputFile.getPath().substring(0, fileExtIdx);
        String fileExtension = inputFile.getPath().substring(fileExtIdx);
        File decryptedFile = new File(fileName + "_decrypted" + fileExtension);
        try {
            if (decryptedFile.createNewFile()) {
                writeBytesToFile(decryptedBytes, decryptedFile);
            } else {
                System.out.println(strings.getString("fileReadErrorMsg"));
            }
        } catch (IOException e) {
            System.out.println(strings.getString("unexpectedErrorMsg"));
        }
    }
}
