package Encryptors;

import Decryptors.Decryptor;
import Decryptors.MwoDecryptor;

/**
 * Encryptor class used to encrypt input files using Multiplication Algorithm.
 */
public class MwoEncryptor extends Encryptor {
    @Override
    protected byte generateKey() {
        // randomize a key between -128 and 127 (1 byte) [without 0
        // and evens] and print it
        byte key = (byte)((randomizer.nextInt(Byte.MAX_VALUE + 1)
                + Byte.MIN_VALUE/2) * 2 + 1);
        System.out.println(strings.getString("keyMsg") + " " + (int)key);
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
     * Get the equivalent decryptor of this encryptor.
     * @return MWO decryptor
     */
    public Decryptor getEquivalentDecryptor() {
        return new MwoDecryptor();
    }
}
