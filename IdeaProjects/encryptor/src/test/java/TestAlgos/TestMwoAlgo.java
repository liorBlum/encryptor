package TestAlgos;

import Algorithms.MwoAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using XOR Algorithm
 */
public class TestMwoAlgo extends AbstractAlgoTest {
    public TestMwoAlgo() {
        super(new MwoAlgo());
    }
    @Test
    public void testMwoEncryption() throws IOException {
        super.testAlgorithm();
    }
}
