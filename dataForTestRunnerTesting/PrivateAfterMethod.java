import nsu.oop.lab2.tester.annotations.After;
import nsu.oop.lab2.tester.annotations.Before;
import nsu.oop.lab2.tester.annotations.Test;

public class PrivateAfterMethod {
    @Before
    public void before() {
        System.out.println("Before-method. Good job!");
    }

    @After
    private void after() {
        System.err.println("Entering into private after-method!");
        throw new RuntimeException("Private after-method");
    }

    @Test
    public void test() {
        System.out.println("Test-method. Good job!");
    }
}