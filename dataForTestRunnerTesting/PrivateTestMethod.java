import nsu.oop.lab2.tester.annotations.After;
import nsu.oop.lab2.tester.annotations.AfterClass;
import nsu.oop.lab2.tester.annotations.Before;
import nsu.oop.lab2.tester.annotations.BeforeClass;
import nsu.oop.lab2.tester.annotations.Test;

public class PrivateTestMethod {
    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass-method. Good job!");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass-method. Good job!");
    }

    @Before
    public void before() {
        System.out.println("Before-method. Good job!");
    }

    @After
    public void after() {
        System.out.println("After-method. Good job!");
    }

    @Test
    private void test() {
        System.err.println("Entering into private test-method!");
        throw new RuntimeException("Private test-method");
    }
}