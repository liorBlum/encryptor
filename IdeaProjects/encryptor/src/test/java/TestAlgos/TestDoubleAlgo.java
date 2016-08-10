package TestAlgos;

import Algorithms.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Test class specified to test encryption using Double Algorithm
 */
public class TestDoubleAlgo extends AbstractAlgoTest {
    public TestDoubleAlgo() {
        super(new DoubleAlgo());
    }

    @Override
    protected byte[] getInputToSend() {
        String asyncOption = strings.getString("asyncOpt");
        String twoAlgosDef = "csr" + ls + "xor";
        String keyPath = exampleFolder.getPath() + "/"
                + strings.getString("keyFileName");
        // test asynchronous execution (encryption and decryption)
        return (twoAlgosDef + ls + asyncOption
                + ls + keyPath + ls + asyncOption).getBytes();
    }

    @Test
    public void testDoubleEncryption() throws IOException {
        super.testAlgorithm();
    }
}
