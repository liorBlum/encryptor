package TestAlgos;

import Algorithms.*;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using Double Algorithm
 */
public class TestDoubleAlgo extends AbstractEncTest {
    public TestDoubleAlgo() {
        super(new DoubleAlgo(new CaesarAlgo(), new MwoAlgo()), "Double Algorithm");
    }
    @Test
    public void testDoubleEncryption() throws IOException {
        super.testEncryption();
    }
}
