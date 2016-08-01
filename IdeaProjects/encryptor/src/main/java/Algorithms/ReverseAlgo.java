package Algorithms;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Scanner;

/**
 * Algorithm class used to encrypt/decrypt input files
 * using Reverse Algorithm.
 */
@XmlRootElement(name="reverseAlgo")
public class ReverseAlgo extends DependentAlgorithm {
    private Algorithm algorithm;

    @Override
    protected void updateAlgorithmMembers(Scanner reader) {
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

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
