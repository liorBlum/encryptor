package Algorithms;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using XOR Algorithm.
 */
public class XORAlgo extends Algorithm {
    /**
     * Encrypt a byte with XOR algorithm.
     * @param b input byte
     * @param keyObject encryption key
     * @return encrypted byte.
     */
    protected byte encryptByte(byte b, Key keyObject) {
        return (byte) (b ^ keyObject.key);
    }

    /**
     * Decrypt a byte with XOR algorithm.
     * @param b input byte
     * @param keyObject decryption key
     * @return decrypted byte.
     */
    protected byte decryptByte(byte b, Key keyObject) {
        return (byte) (b ^ keyObject.key);
    }
}
