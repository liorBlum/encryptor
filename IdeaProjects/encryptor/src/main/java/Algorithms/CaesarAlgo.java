package Algorithms;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Caesar Algorithm.
 */
public class CaesarAlgo extends Algorithm {
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
     * Decrypt a byte with Caesar algorithm.
     * @param b input byte
     * @param key encryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, byte key) {
        return (byte) (b - key);
    }
}
