import nsu.oop.lab2.tester.annotations.After;
import nsu.oop.lab2.tester.annotations.AfterClass;
import nsu.oop.lab2.tester.annotations.Before;
import nsu.oop.lab2.tester.annotations.BeforeClass;
import nsu.oop.lab2.tester.annotations.Test;

public class PrivateAfterMethod {
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
    private void after() {
        System.err.println("Entering into private after-method!");
        throw new RuntimeException("Private after-method");
    }

    @Test
    public void test() {
        System.out.println("Test-method. Good job!");
    }
}