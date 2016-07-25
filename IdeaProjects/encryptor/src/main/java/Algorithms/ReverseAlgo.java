package Algorithms;

import java.io.IOException;

/**
 * Created by Lior on 25/07/2016.
 */
public class ReverseAlgo extends DependentAlgorithm {
    private Algorithm algorithm;

    @Override
    protected void updateAlgorithmMembers() {
        System.out.println(strings.getString("rvsMsg"));
        showIndepAlgorithmsSelection();
        algorithm = getIndepAlgorithmFromUser();
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
