package Algorithms;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using XOR Algorithm.
 */
public class XORAlgo extends Algorithm {
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
     * Decrypt a byte with XOR algorithm.
     * @param b input byte
     * @param key decryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, byte key) {
        return (byte) (b^key);
    }
}
