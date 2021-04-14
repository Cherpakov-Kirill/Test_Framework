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

    @Test
    public void objectEquals() {
        String s1 = "String";
        String s2 = "Str";
        Assert.assertNotEquals(s1, s2);
        s2 += "ing";
        Assert.assertEquals(s1, s2);
    }

    @Test
    public void longEquals() {
        Assert.assertEquals(5000000, 5000000);
        Assert.assertNotEquals(5000000, 5000001);
    }

    @Test
    public void doubleEqualsTest() {
        Assert.assertEquals(0.00055, 0.00055, 1e-10);
        Assert.assertNotEquals(0.00055, 0.00054, 1e-10);
    }

    @Test
    public void floatEqualsTest() {
        Assert.assertEquals((float) 0.55, (float) 0.55, (float) 1e-3);
        Assert.assertNotEquals((float) 0.55, (float) 0.45, (float) 1e-3);
    }

    @Test(expected = AssertException.class)
    public void doubleNotEquals() {
        Assert.assertNotEquals(0.00055, 0.00055, 1e-10);
    }

    @Test
    public void booleanArray() {
        boolean[] a = new boolean[3];
        boolean[] b = new boolean[3];
        ///in default there are false in all elements of array
        a[0] = true;
        b[0] = true;
        Assert.assertArrayEquals(a, b);
    }

    @Test(expected = AssertException.class)
    public void arrayNotEquals() {
        int[][] a = new int[2][3];
        a[0][0] = 1;
        a[0][1] = 1;
        a[0][2] = 1;
        a[1][0] = 1;
        a[1][1] = 1;
        a[1][2] = 1;
        int[][] b = new int[2][3];
        b[0][0] = 1;
        b[0][1] = 1;
        b[0][2] = 5;
        b[1][0] = 1;
        b[1][1] = 1;
        b[1][2] = 5;
        //Send to object because int[][]
        Assert.assertArrayEquals(a, b);
    }
}