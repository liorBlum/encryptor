package TestAlgos;

import Algorithms.ReverseAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using Reverse Algorithm
 */
public class TestReverseAlgo extends AbstractAlgoTest {
    public TestReverseAlgo() {
        super(new ReverseAlgo());
    }
    @Override
    protected byte[] getInputToSend() {
        String asyncOption = strings.getString("asyncOpt");
        String algoDef = "mwo";
        String keyPath = exampleFolder.getPath() + "/"
                + strings.getString("keyFileName");
        // test asynchronous execution (encryption and decryption)
        return (algoDef + ls + asyncOption
                + ls + keyPath + ls + asyncOption).getBytes();
    }

    @Test
    public void testReverseEncryption() throws IOException {
        super.testAlgorithm();
    }
}
