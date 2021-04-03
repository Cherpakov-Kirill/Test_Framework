import org.junit.Test;
import nsu.oop.lab2.tester.exceptions.AssertException;
import nsu.oop.lab2.tester.Assert;

public class TestForAsserts {
    @Test(expected = AssertException.class)
    public void condition() {
        Assert.assertTrue(false);
    }

    @Test(expected = AssertionError.class)
    public void myStrangeTest() {
        Assert.assertTrue("this is true", true);
        throw new AssertionError();
    }

    @Test(expected = AssertException.class)
    public void myStupidTest() {
        Assert.assertFalse("this is true", true);
        throw new AssertionError();
    }

    @Test(expected = AssertException.class)
    public void forNull() {
        Assert.assertNotNull(null);
    }

    @Test(expected = AssertException.class)
    public void forSame() {
        int a1 = 5;
        int a2 = 5;
        Assert.assertNotSame(a1, a2);
    }

    @Test(expected = AssertException.class)
    public void doubleEquals() {
        Assert.assertNotEquals(0.00055, 0.00055, 1e-10);
    }

    @Test(expected = AssertException.class)
    public void arrayEquals() {
        int[] a = new int[2];
        a[0] = 5;
        a[1] = 9;
        int[] b = new int[2];
        b[0] = 5;
        b[1] = 15;
        Assert.assertArrayEquals(a, b);
    }
}