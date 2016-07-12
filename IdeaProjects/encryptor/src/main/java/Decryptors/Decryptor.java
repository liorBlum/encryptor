package Decryptors;

import Utilities.FileModifier;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
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
     * Get the encryption key between -128 and 127 from the user input
     * @return the decryption key
     */
    protected byte getInputKey(Scanner reader) throws
            InvalidParameterException {
        String input;
        System.out.println(strings.getString("keyInputMsg"));
        input = reader.nextLine();
        if (Integer.parseInt(input) >= Byte.MIN_VALUE
                && Integer.parseInt(input) <= Byte.MAX_VALUE) {
            return Byte.parseByte(input);
        }
        else {
            throw new IllegalArgumentException(
                    strings.getString("keyInputErrorMsg"));
        }
    }
    /**
     * Decrypt an input file and write the result in a new file.
     * @param inputFile an input file
     */
    public void decrypt(File inputFile) {
        Scanner reader = new Scanner(System.in);
        byte key;
        // initialize a byte array to contain decrypted bytes
        int fileLength = (int) inputFile.length();
        byte[] decryptedBytes = new byte[fileLength];
        // get a decryption key from the user
        while (true) {
            try {
                key = getInputKey(reader);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
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
            File decryptedFile = createFileInPath(fileName
                    + "_decrypted" + fileExtension);
            writeBytesToFile(decryptedBytes, decryptedFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
