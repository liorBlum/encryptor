package TestAlgos;

import Algorithms.MwoAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using XOR Algorithm
 */
public class TestMwoAlgo extends AbstractEncTest {
    public TestMwoAlgo() {
        super(new MwoAlgo(), "Multiplication Algorithm");
    }
    @Test
    public void testMwoEncryption() throws IOException {
        super.testEncryption();
    }
}
