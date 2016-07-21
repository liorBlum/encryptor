package Algorithms;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Caesar Algorithm.
 */
public class CaesarAlgo extends Algorithm {
    /**
     * Encrypt a byte with Caesar algorithm.
     * @param b input byte
     * @param keyObject encryption key
     * @return encrypted byte.
     */
    protected byte encryptByte(byte b, Key keyObject) {
        return (byte)(b + keyObject.key);
    }

    /**
     * Decrypt a byte with Caesar algorithm.
     * @param b input byte
     * @param keyObject encryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, Key keyObject) {
        return (byte) (b - keyObject.key);
    }
}
