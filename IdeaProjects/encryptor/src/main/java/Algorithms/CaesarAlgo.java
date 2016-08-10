package Algorithms;


import com.google.inject.Singleton;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Caesar Algorithm.
 */
@XmlRootElement(name="caesarAlgo")
public class CaesarAlgo extends IndependentAlgorithm {
    /**
     * Algorithm constructor that is used to set the Algorithm's name
     */
    public CaesarAlgo() {
        super("Caesar Algorithm");
    }
    @Override
    protected byte encryptByte(byte b, int idx, Key keyObject) {
        return (byte)(b + keyObject.key);
    }

    @Override
    protected byte decryptByte(byte b, int idx, Key keyObject) {
        return (byte) (b - keyObject.key);
    }
}
