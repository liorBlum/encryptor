package TestAlgos;

import Algorithms.SplitAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 *Test class specified to test encryption using Split Algorithm
 */
public class TestSplitAlgo extends AbstractAlgoTest {
    public TestSplitAlgo() {
        super(new SplitAlgo());
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
    public void testSplitEncryption() throws IOException {
        super.testAlgorithm();
    }
}
