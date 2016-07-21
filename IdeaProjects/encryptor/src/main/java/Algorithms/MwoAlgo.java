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
    protected Key getInputKey(Scanner reader) throws
            IOException {
        byte encryptionKey = super.getInputKey(reader).key;
        // encryption key for MWO can't be even or 0
        if (encryptionKey == 0 || ((encryptionKey & 1) == 0)) {
            throw new InvalidParameterException(
                    strings.getString("keyMWOErrorMsg"));
        }
        int i;
        // find and return decryption key
        for (i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
            if ((byte)(encryptionKey * i) == 1) {
                return new Key((byte)i);
            }
        }
        // if all bytes in range (min - (max)) are not decryption key,
        // throw an exception
        throw new InvalidParameterException(
                strings.getString("keyMWOErrorMsg"));
    }

    @Override
    protected Key generateKey() {
        // randomize a key between -128 and 127 (1 byte) [without 0
        // and evens] and return it
        byte key = (byte)((randomizer.nextInt(Byte.MAX_VALUE + 1)
                + Byte.MIN_VALUE/2) * 2 + 1);
        return new Key(key);
    }
    /**
     * Encrypt a byte with Multiplication algorithm.
     * @param b input byte
     * @param keyObject encryption key
     * @return encrypted byte.
     */
    protected byte encryptByte(byte b, Key keyObject) {
        return (byte) (b * keyObject.key);
    }

    /**
     * Decrypt a byte with Multiplication algorithm.
     * @param b input byte
     * @param keyObject decryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, Key keyObject) {
        return (byte) (b * keyObject.key);
    }

}
