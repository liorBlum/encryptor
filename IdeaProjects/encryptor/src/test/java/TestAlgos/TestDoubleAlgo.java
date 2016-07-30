package TestAlgos;

import Algorithms.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Test class specified to test encryption using Double Algorithm
 */
public class TestDoubleAlgo extends AbstractAlgoTest {
    public TestDoubleAlgo() {
        super(new DoubleAlgo(), "Double Algorithm");
    }

    @Override
    protected byte[] getInputToSend() {
        String twoAlgosDef = independentAlgosCodes.getString("Caesar Algorithm")
                + ls
                + independentAlgosCodes.getString("XOR Algorithm");
        String keyPath = exampleFolder.getPath() + "/"
                + strings.getString("keyFileName");
        return (twoAlgosDef + ls + twoAlgosDef + ls + keyPath).getBytes();
    }

    @Test
    public void testDoubleEncryption() throws IOException {
        super.testAlgorithm();
    }
}
