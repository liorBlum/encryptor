package TestAlgos;

import Algorithms.ReverseAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using Reverse Algorithm
 */
public class TestReverseAlgo extends AbstractAlgoTest {
    public TestReverseAlgo() {
        super(new ReverseAlgo(), "Reverse Algorithm");
    }
    @Override
    protected byte[] getInputToSend() {
        String algoDef = independentAlgosCodes.getString(
                "Multiplication Algorithm");
        String keyPath = exampleFolder.getPath() + "/"
                + strings.getString("keyFileName");
        return (algoDef + ls + algoDef + ls + keyPath).getBytes();
    }

    @Test
    public void testReverseEncryption() throws IOException {
        super.testAlgorithm();
    }
}
