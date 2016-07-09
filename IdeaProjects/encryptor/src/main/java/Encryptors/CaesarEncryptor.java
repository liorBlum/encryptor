package Encryptors;

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
        // without a key, the method returns the original byte
        if (key == -1) {
            return b;
        } else if (b + key > (int)Byte.MAX_VALUE) {
            return (byte)(b + key - Byte.MAX_VALUE + Byte.MIN_VALUE - 1);
        } else {
            return (byte)(b + key);
        }
    }
}
