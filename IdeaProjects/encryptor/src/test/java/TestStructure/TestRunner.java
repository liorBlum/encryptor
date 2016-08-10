package TestStructure;
/**
 * Test runner that runs all the tests
 */
import TestAlgos.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestMenu.class,
               TestCaesarEncryptor.class, TestXORAlgo.class,
                TestMwoAlgo.class, TestDoubleAlgo.class,
                TestReverseAlgo.class, TestSplitAlgo.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
