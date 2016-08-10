package Algorithms;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using XOR Algorithm.
 */
@XmlRootElement(name="xorAlgo")
public class XORAlgo extends IndependentAlgorithm {
    /**
     * Algorithm constructor that is used to set the Algorithm's name
     */
    public XORAlgo() {
        super("XOR Algorithm");
    }
    @Override
    protected byte encryptByte(byte b, int idx, Key keyObject) {
        return (byte) (b ^ keyObject.key);
    }

    @Override
    protected byte decryptByte(byte b, int idx, Key keyObject) {
        return (byte) (b ^ keyObject.key);
    }
}
