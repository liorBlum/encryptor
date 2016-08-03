package Algorithms;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Scanner;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Reverse Algorithm.
 */
@XmlRootElement(name="reverseAlgo")
public class ReverseAlgo extends DependentAlgorithm {
    /**
     * Get and set inner algorithm used by Reverse Algorithm
     * @param algorithm algorithm
     * @return algorithm
     */
    @Getter @Setter private Algorithm algorithm;

    /**
     * Algorithm constructor that is used to set the Algorithm's name
     */
    public ReverseAlgo() {
        super("Reverse Algorithm");
    }

    @Override
    protected void updateAlgorithmMembers(Scanner reader) throws IOException {
        System.out.println(strings.getString("rvsMsg"));
        showIndepAlgorithmsSelection();
        algorithm = getIndepAlgorithmFromUser(reader);
    }

    @Override
    protected Key generateKey() {
        return algorithm.generateKey();
    }

    @Override
    protected Key getDecryptionKey(Key encryptionKey) throws Exception {
        return algorithm.getDecryptionKey(encryptionKey);
    }

    @Override
    protected byte encryptByte(byte b, int idx, Key key) throws IOException {
        return algorithm.decryptByte(b, idx, key);
    }

    @Override
    protected byte decryptByte(byte b, int idx, Key key) throws IOException {
        return algorithm.encryptByte(b, idx, key);
    }
}
