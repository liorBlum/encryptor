package Algorithms;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Caesar Algorithm.
 */
@XmlRootElement(name="caeserAlgo")
public class CaesarAlgo extends Algorithm {

    @Override
    protected byte encryptByte(byte b, int idx, Key keyObject) {
        return (byte)(b + keyObject.key);
    }

    @Override
    protected byte decryptByte(byte b, int idx, Key keyObject) {
        return (byte) (b - keyObject.key);
    }
}
