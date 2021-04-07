package nsu.oop.lab2.tester;

import nsu.oop.lab2.tester.domestic.ExactArraysComparison;
import nsu.oop.lab2.tester.domestic.InexactArraysComparison;
import nsu.oop.lab2.tester.exceptions.AssertException;

public class Assert {
    // CR: why protected?
    protected Assert() {
    }

    // CR: why not private
    ///formatting failures message
    static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null && !message.equals("")) formatted = message + "\n";
        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        // CR: please use if else or even extract to separate method, now it's hard to read
        return expectedString.equals(actualString) ? formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString) : formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
    }

    private static String formatClassAndValue(Object value, String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }

    ///Fail()
    public static void fail(String message) {
        // CR: can move this logic to AssertException constructor
        if (message == null) {
            throw new AssertException();
        } else {
            throw new AssertException("Assert failed: " + message);
        }
    }

    public static void fail() {
        fail(null);
    }

    ///Assert true
    public static void assertTrue(String message, boolean condition) {
        if (!condition) fail("assertTrue is not passed\n" + message);
    }

    public static void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }


    ///Assert false
    public static void assertFalse(String message, boolean condition) {
        if (condition) fail("assertFalse is not passed\n" + message);
    }

    public static void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    ///Asserts for null/not null
    public static void failNotNull(String message, Object actual) {
        String failureMessage = "";
        if (message != null) failureMessage = message + " ";
        fail("assertNull is not passed\n" + failureMessage + "expected null, but was:<" + actual + ">");
    }

    public static void assertNotNull(String message, Object object) {
        if (object == null) fail("assertNotNull is not passed\n" + message);
    }

    public static void assertNotNull(Object object) {
        assertNotNull(null, object);
    }

    public static void assertNull(String message, Object object) {
        if (object != null) failNotNull(message, object);
    }

    public static void assertNull(Object object) {
        assertNull(null, object);
    }

    ///Asserts for same/not same
    private static void failSame(String message) {
        // CR: ternary
        String failureMessage = "";
        if (message != null) failureMessage = message + " ";
        fail("assertNotSame is not passed\n" + failureMessage + "expected not same");
    }

    private static void failNotSame(String message, Object expected, Object actual) {
        String failureMessage = "";
        if (message != null) failureMessage = message + " ";
        fail("assertSame is not passed\n" + failureMessage + "expected same:<" + expected + "> was not:<" + actual + ">");
    }

    public static void assertSame(String message, Object expected, Object actual) {
        if (expected != actual) failNotSame(message, expected, actual);
    }

    public static void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    public static void assertNotSame(String message, Object unexpected, Object actual) {
        if (unexpected == actual) failSame(message);
    }

    public static void assertNotSame(Object unexpected, Object actual) {
        assertNotSame(null, unexpected, actual);
    }

    ///Send failure with not equals
    private static void failNotEquals(String message, Object expected, Object actual) {
        fail("assertEquals is not passed\n" + format(message, expected, actual));
    }

    ///Asserts equals for objects, not for long, double, float
    private static boolean equalsRegardingNull(Object expected, Object actual) {
        if (expected == null) return actual == null;
        // CR: can optimize and compare with == first
        else return expected.equals(actual);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (!equalsRegardingNull(expected, actual)) failNotEquals(message, expected, actual);
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    // CR: why don't you have the same method e.g. for ints?
    ///Asserts equals for long, double, float
    public static void assertEquals(String message, long expected, long actual) {
        if (expected != actual) failNotEquals(message, expected, actual);
    }

    public static void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, double expected, double actual, double delta) {
        if (doubleIsDifferent(expected, actual, delta)) failNotEquals(message, expected, actual);
    }

    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    public static void assertEquals(String message, float expected, float actual, float delta) {
        if (floatIsDifferent(expected, actual, delta)) failNotEquals(message, expected, actual);
    }

    public static void assertEquals(float expected, float actual, float delta) {
        assertEquals(null, expected, actual, delta);
    }


    ///Assert not equals
    private static void failEquals(String message, Object unexpected, Object actual) {
        String failureMessage = "";
        if (message != null) failureMessage = message + ".\n";
        failureMessage = failureMessage + "Values should be different. ";
        failureMessage = failureMessage + "Unexpected was: " + unexpected + "but was actual: " + actual;
        fail("assertNotEquals is not passed\n" + failureMessage);
    }

    ///Assert not equals for objects, not for long, double, float
    public static void assertNotEquals(String message, Object unexpected, Object actual) {
        if (equalsRegardingNull(unexpected, actual)) {
            failEquals(message, unexpected, actual); //fail with same objects, it should be not equals
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        assertNotEquals(null, unexpected, actual);
    }

    public static void assertNotEquals(String message, long unexpected, long actual) {
        if (unexpected == actual) failEquals(message, unexpected, actual);
    }

    public static void assertNotEquals(long unexpected, long actual) {
        assertNotEquals(null, unexpected, actual);
    }

    public static void assertNotEquals(String message, double unexpected, double actual, double delta) {
        if (!doubleIsDifferent(unexpected, actual, delta)) failEquals(message, unexpected, actual);
    }

    public static void assertNotEquals(double unexpected, double actual, double delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    public static void assertNotEquals(String message, float unexpected, float actual, float delta) {
        if (!floatIsDifferent(unexpected, actual, delta)) failEquals(message, unexpected, actual);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    ///checking for differences of float or double
    private static boolean doubleIsDifferent(double d1, double d2, double delta) {
        return !(Math.abs(d1 - d2) <= delta);
    }

    private static boolean floatIsDifferent(float f1, float f2, float delta) {
        return !(Math.abs(f1 - f2) <= delta);
    }


    ///Asserts for many types

    ///Object[]
    public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(Object[] expecteds, Object[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    // CR: I'm not sure that you avoid boxing here, please check
    ///Boolean[]
    public static void assertArrayEquals(String message, boolean[] expecteds, boolean[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    ///Byte[]
    public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(byte[] expecteds, byte[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    ///Char[]
    public static void assertArrayEquals(String message, char[] expecteds, char[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(char[] expecteds, char[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    ///Short[]
    public static void assertArrayEquals(String message, short[] expecteds, short[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(short[] expecteds, short[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    ///Int[]
    public static void assertArrayEquals(String message, int[] expecteds, int[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(int[] expecteds, int[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    ///Long[]
    public static void assertArrayEquals(String message, long[] expecteds, long[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(long[] expecteds, long[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    ///Double[]
    public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta) {
        (new InexactArraysComparison(delta)).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }

    ///Float[]
    public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta) {
        (new InexactArraysComparison(delta)).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }
}

