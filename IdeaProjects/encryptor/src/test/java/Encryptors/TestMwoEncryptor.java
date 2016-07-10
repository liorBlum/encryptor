package Encryptors;

import Decryptors.MwoDecryptor;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using XOR Algorithm
 */
public class TestMwoEncryptor extends AbstractEncTest {
    public TestMwoEncryptor() {
        super(new MwoEncryptor(), new MwoDecryptor());
    }
    @Test
    public void testMwoEncryption() throws IOException {
        super.testEncryption();
    }
}
