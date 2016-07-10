package Encryptors;

import Decryptors.CaesarDecryptor;
import org.junit.Test;

import java.io.*;

/**
 * Test class specified to test encryption using Caesar Algorithm
 */
public class TestCaesarEncryptor extends AbstractEncTest {
    public TestCaesarEncryptor() {
        super(new CaesarEncryptor(), new CaesarDecryptor());
    }
    @Test
    public void testCaesarEncryption() throws IOException{
        super.testEncryption();
    }
}
