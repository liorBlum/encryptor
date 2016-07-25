package TestStructure;
/**
 * Test runner that runs all the tests
 */
import TestAlgos.TestCaesarEncryptor;
import TestAlgos.TestDoubleAlgo;
import TestAlgos.TestMwoAlgo;
import TestAlgos.TestXORAlgo;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestMenu.class,
               TestCaesarEncryptor.class, TestXORAlgo.class,
                TestMwoAlgo.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
