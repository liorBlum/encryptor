package Algorithms;

import Utilities.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Multiplication Algorithm.
 */
public class MwoAlgo extends Algorithm {
    /**
     * Get encryption key from the user and find decryption key for MWO
     * @return decryption key
     */
    @Override
    protected byte getInputKey(Scanner reader) throws
            Exception {
        byte encryptionKey = super.getInputKey(reader);
        // encryption key for MWO can't be even or 0
        if (encryptionKey == 0 || ((encryptionKey & 1) == 0)) {
            throw new InvalidParameterException(
                    strings.getString("keyMWOErrorMsg"));
        }
        int i;
        for (i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
            if ((byte)(encryptionKey * i) == 1) {
                return (byte)i;
            }
        }
        // if all bytes in range (min - (max-1)) are not decryption key,
        // it must be max
        return (byte)i;
    }

    @Override
    protected byte generateKey(File encryptedFile) throws IOException{
        // randomize a key between -128 and 127 (1 byte) [without 0
        // and evens] and print it
        byte key = (byte)((randomizer.nextInt(Byte.MAX_VALUE + 1)
                + Byte.MIN_VALUE/2) * 2 + 1);
        File keyFile = new File(encryptedFile.getParent(),
                strings.getString("keyFileName"));
        SerializationUtils.serializeObject(keyFile, new Key(key));
        System.out.println(strings.getString("keyMsg"));
        return key;
    }
    /**
     * Encrypt a byte with Multiplication algorithm.
     * @param b input byte
     * @param key encryption key
     * @return encrypted byte.
     */
    protected byte encryptByte(byte b, byte key) {
        return (byte) (b * key);
    }

    /**
     * Decrypt a byte with Multiplication algorithm.
     * @param b input byte
     * @param key decryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, byte key) {
        return (byte) (b * key);
    }

}
