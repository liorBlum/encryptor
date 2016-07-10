package Structure;
/**
 * Test runner that runs all the tests
 */
import Encryptors.TestCaesarEncryptor;
import Encryptors.TestXOREncryptor;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestMenu.class,
                TestCaesarEncryptor.class, TestXOREncryptor.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
