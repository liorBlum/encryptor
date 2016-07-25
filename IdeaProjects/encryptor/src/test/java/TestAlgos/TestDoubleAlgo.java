package TestAlgos;

import Algorithms.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Test class specified to test encryption using Double Algorithm
 */
public class TestDoubleAlgo extends AbstractEncTest {
    public TestDoubleAlgo() {
        super(new DoubleAlgo(), "Double Algorithm");
    }
    @Test
    public void testDoubleEncryption() throws IOException {
        // send requested algorithms to algorithm test
        InputStream isIn = new ByteArrayInputStream(
                ("csr" + System.getProperty("line.separator")
                        + "mwo").getBytes());
        System.setIn(isIn);
        super.testAlgorithm();
        System.setIn(defInStream);
    }
}
