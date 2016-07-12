package Decryptors;

import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * Created by Lior on 10/07/2016.
 */
public class MwoDecryptor extends Decryptor {
    /**
     * Get encryption key from the user and find decryption key for MWO
     * @return decryption key
     */
    @Override
    protected byte getInputKey(Scanner reader) throws
            InvalidParameterException {
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
