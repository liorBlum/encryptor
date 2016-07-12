package Structure;
/**
 * Test runner that runs all the tests
 */
import TestEncryptors.TestCaesarEncryptor;
import TestEncryptors.TestMwoEncryptor;
import TestEncryptors.TestXOREncryptor;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestMenu.class,
                TestCaesarEncryptor.class, TestXOREncryptor.class,
                TestMwoEncryptor.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
