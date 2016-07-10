package Encryptors;

import Decryptors.XORDecryptor;
import org.junit.Test;

import java.io.IOException;

/**
 * Test class specified to test encryption using XOR Algorithm
 */
public class TestXOREncryptor extends AbstractEncTest {
        public TestXOREncryptor() {
            super(new XOREncryptor(), new XORDecryptor());
        }
        @Test
        public void testXOREncryption() throws IOException {
            super.testEncryption();
        }

}
