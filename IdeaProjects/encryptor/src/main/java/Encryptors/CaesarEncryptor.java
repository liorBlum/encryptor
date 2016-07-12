package Encryptors;

import Decryptors.CaesarDecryptor;
import Decryptors.Decryptor;

/**
 * Encryptor class used to encrypt input files using Caesar Algorithm.
 */
public class CaesarEncryptor extends Encryptor {
    /**
     * Encrypt a byte with Caesar algorithm.
     * @param b input byte
     * @param key encryption key
     * @return encrypted byte.
     */
    protected byte encryptByte(byte b, byte key) {
        return (byte)(b + key);
    }
    /**
     * Get the equivalent decryptor of this encryptor.
     * @return Caesar decryptor
     */
    public Decryptor getEquivalentDecryptor() {
        return new CaesarDecryptor();
    }
}
