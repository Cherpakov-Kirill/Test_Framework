import nsu.oop.lab2.tester.annotations.After;
import nsu.oop.lab2.tester.annotations.Before;
import nsu.oop.lab2.tester.annotations.Test;

public class PrivateBeforeMethod {
    @Before
    private void before() {
        System.err.println("Entering into private before-method!");
        throw new RuntimeException("Private before-method");
    }

    @After
    public void after() {
        System.out.println("After-method. Good job!");
    }

    @Test
    public void test() {
        System.out.println("Test-method. Good job!");
    }
}