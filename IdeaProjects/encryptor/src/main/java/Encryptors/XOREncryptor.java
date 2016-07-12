package Encryptors;

import Decryptors.Decryptor;
import Decryptors.XORDecryptor;

/**
 * Encryptor class used to encrypt input files using XOR Algorithm.
 */
public class XOREncryptor extends Encryptor {
    /**
     * Encrypt a byte with XOR algorithm.
     * @param b input byte
     * @param key encryption key
     * @return encrypted byte.
     */
    protected byte encryptByte(byte b, byte key) {
        return (byte) (b^key);
    }
    /**
     * Get the equivalent decryptor of this encryptor.
     * @return XOR decryptor
     */
    public Decryptor getEquivalentDecryptor() {
        return new XORDecryptor();
    }
}
