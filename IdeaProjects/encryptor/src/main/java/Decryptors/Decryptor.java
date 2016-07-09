package Decryptors;

import Structure.FileModifier;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Abstract class for encryptors
 */
public abstract class Decryptor extends FileModifier {
    /**
     * Decrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param key decryption key
     * @return decrypted byte.
     */
    protected abstract byte decryptByte(byte b, byte key);
    /**
     * Get a decryption key between 0 and 127 from the user input
     * @return the decryption key
     */
    protected byte getInputKey() {
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
     * Decrypt an input file and write the result in a new file.
     * @param inputFile an input file
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
            decryptedBytes[i] = decryptByte(decryptedBytes[i], key);
        }
        // write the decrypted bytes to a new file
        int fileExtIdx = inputFile.getPath().lastIndexOf('.');
        if (fileExtIdx == -1) {
            fileExtIdx = inputFile.getPath().length();
        }
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
