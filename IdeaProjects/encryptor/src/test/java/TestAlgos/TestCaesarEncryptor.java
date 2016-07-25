package TestAlgos;

import Algorithms.CaesarAlgo;
import org.junit.Test;

import java.io.*;

/**
 * Test class specified to test encryption using Caesar Algorithm
 */
public class TestCaesarEncryptor extends AbstractEncTest {
    public TestCaesarEncryptor() {
        super(new CaesarAlgo(), "Caesar Algorithm");
    }
    @Test
    public void testCaesarEncryption() throws IOException{
        super.testAlgorithm();
    }
}
