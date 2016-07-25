package TestAlgos;

import Algorithms.XORAlgo;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using XOR Algorithm
 */
public class TestXORAlgo extends AbstractEncTest {
        public TestXORAlgo() {
            super(new XORAlgo(), "XOR Algorithm");
        }
        @Test
        public void testXOREncryption() throws IOException {
            super.testAlgorithm();
        }

}
