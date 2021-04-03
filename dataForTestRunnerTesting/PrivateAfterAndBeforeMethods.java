import nsu.oop.lab2.tester.annotations.After;
import nsu.oop.lab2.tester.annotations.Before;
import nsu.oop.lab2.tester.annotations.Test;

public class PrivateAfterAndBeforeMethods {
    @Before
    private void before() {
        System.err.println("Entering into private before-method!");
        throw new RuntimeException("Private before-method");
    }

    @After
    private void after() {
        System.out.println("Entering into private after-method!");
        throw new RuntimeException("Private after-method");
    }

    @Test
    public void test() {
        System.out.println("Test-method. Good job!");
    }
}