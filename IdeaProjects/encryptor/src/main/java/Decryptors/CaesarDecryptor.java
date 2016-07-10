package Decryptors;
/**
 * Decryptor class used to decrypt input files using Caesar Algorithm.
 */
public class CaesarDecryptor extends Decryptor {
    /**
     * Decrypt a byte with Caesar algorithm.
     * @param b input byte
     * @param key decryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, byte key) {
        // without a key, the method returns the original byte
        if (key == -1) {
            return b;
        } else if (b - key < (int) Byte.MIN_VALUE) {
            return  (byte) (b - key + Byte.MAX_VALUE - Byte.MIN_VALUE + 1);
        } else {
            return  (byte) (b - key);
        }
    }
}
