package Encryptors;

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
        // without a key, the method returns the original byte
        if (key == -1) {
            return b;
        }
        return (byte) (b^key);
    }
}