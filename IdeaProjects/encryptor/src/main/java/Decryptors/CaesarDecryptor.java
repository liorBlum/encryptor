package Decryptors;
/**
 * Decryptor class used to decrypt input files using Caesar Algorithm.
 */
public class CaesarDecryptor extends Decryptor {
    /**
     * Decrypt a byte with Caesar algorithm.
     * @param b input byte
     * @param key encryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, byte key) {
        return (byte) (b - key);
    }
}
