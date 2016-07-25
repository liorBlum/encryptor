package TestAlgos;

import Algorithms.SplitAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Lior on 26/07/2016.
 */
public class TestSplitAlgo extends AbstractDepAlgoTest {
    public TestSplitAlgo() {
        super(new SplitAlgo(), "Split Algorithm");
    }
    @Override
    protected byte[] getInputToSend() {
        String algoDef = independentAlgosCodes.getString(
                "Multiplication Algorithm");
        String keyPath = strings.getString("keyFileName");
        return (algoDef + ls + algoDef + ls + keyPath).getBytes();
    }

    @Test
    public void testSplitEncryption() throws IOException {
        super.testAlgorithm();
    }
}
