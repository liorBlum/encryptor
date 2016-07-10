package Decryptors;



/**
 * Encryptor class used to encrypt input files using XOR Algorithm.
 */
public class XORDecryptor extends Decryptor {
    /**
     * Decrypt a byte with XOR algorithm.
     * @param b input byte
     * @param key decryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, byte key) {
        // without a key, the method returns the original byte
        if (key == -1) {
            return b;
        }
        return (byte) (b^key);
    }
}
