package TestAlgos;

import Algorithms.SplitAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 *Test class specified to test encryption using Split Algorithm
 */
public class TestSplitAlgo extends AbstractAlgoTest {
    public TestSplitAlgo() {
        super(new SplitAlgo(), "Split Algorithm");
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
    public void testSplitEncryption() throws IOException {
        super.testAlgorithm();
    }
}
