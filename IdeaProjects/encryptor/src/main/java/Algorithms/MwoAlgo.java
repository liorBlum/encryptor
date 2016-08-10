package Algorithms;


import com.google.inject.Singleton;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Scanner;
/**
 * Algorithm class used to encrypt/decrypt input files
 * using Multiplication Algorithm.
 */
@XmlRootElement(name="mwoAlgo")
public class MwoAlgo extends IndependentAlgorithm {
    /**
     * Algorithm constructor that is used to set the Algorithm's name
     */
    public MwoAlgo() {
        super("Multiplication Algorithm");
    }

    /**
     * Get encryption key from the user and find decryption key for MWO
     * @return decryption key
     */
    @Override
    protected Key getInputKey(Scanner reader) throws
            Exception {
        byte encryptionKey = super.getInputKey(reader).key;
        // encryption key for MWO can't be even or 0
        if (encryptionKey == 0 || ((encryptionKey & 1) == 0)) {
            throw new IllegalArgumentException (
                    strings.getString("keyMWOErrorMsg"));
        } else {
            return new Key(encryptionKey);
        }
    }

    @Override
    protected Key getDecryptionKey(Key encryptionKey) throws Exception {
        // encryption key for MWO can't be even or 0
        if (encryptionKey.key == 0 || ((encryptionKey.key & 1) == 0)) {
            throw new IllegalArgumentException(
                    strings.getString("keyMWOErrorMsg"));
        }
        int i;
        byte encKeyByte = encryptionKey.key;
        // find and return decryption key
        for (i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
            if ((byte)(encKeyByte * i) == 1) {
                return new Key((byte)i);
            }
        }
        // if all bytes in range (min - (max)) are not decryption key,
        // throw an exception
        throw new RuntimeException(
                strings.getString("unexpectedErrorMsg"));
    }

    @Override
    protected Key generateKey() {
        // randomize a key between -128 and 127 (1 byte) [without 0
        // and evens] and return it
        byte key = (byte)((randomizer.nextInt(Byte.MAX_VALUE + 1)
                + Byte.MIN_VALUE/2) * 2 + 1);
        return new Key(key);
    }

    @Override
    protected byte encryptByte(byte b, int idx, Key keyObject) {
        return (byte) (b * keyObject.key);
    }

    @Override
    protected byte decryptByte(byte b, int idx, Key keyObject) {
        return (byte) (b * keyObject.key);
    }

}
