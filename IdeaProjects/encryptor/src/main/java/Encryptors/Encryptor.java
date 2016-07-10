package Encryptors;

import Structure.FileModifier;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Abstract class for encryptors
 */
public abstract class Encryptor extends FileModifier{
    protected Random randomizer = new Random();

    /**
     * generate a random encryption key.
     * @return encryption key
     */
    protected byte generateKey() {
        // randomize a key between -128 and 127 (1 byte) and print it
        byte key = (byte)(randomizer.nextInt(2*Byte.MAX_VALUE + 2)
                + Byte.MIN_VALUE);
        System.out.println(strings.getString("keyMsg") + " " + (int)key);
        return key;
    }

    /**
     * Encrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param key encryption key
     * @return encrypted byte.
     */
    protected abstract byte encryptByte(byte b, byte key);
    /**
     * Encrypt an input file and write the result in a new file.
     * @param inputFile an input file
     */
    public void encrypt(File inputFile) {
        byte key = generateKey();
        // initialize a byte array to contain encrypted bytes
        int fileLength = (int)inputFile.length();
        byte[] encryptedBytes = new byte[fileLength];
        // read the file and encrypt it byte by byte.
        readBytesFromFile(encryptedBytes, inputFile);
        for (int i = 0; i < fileLength; i++) {
            encryptedBytes[i] = encryptByte(encryptedBytes[i], key);
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
