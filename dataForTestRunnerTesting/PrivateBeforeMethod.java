import nsu.oop.lab2.tester.annotations.After;
import nsu.oop.lab2.tester.annotations.AfterClass;
import nsu.oop.lab2.tester.annotations.Before;
import nsu.oop.lab2.tester.annotations.BeforeClass;
import nsu.oop.lab2.tester.annotations.Test;

public class PrivateBeforeMethod {
    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass-method. Good job!");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass-method. Good job!");
    }

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