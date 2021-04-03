package nsu.oop.lab2.tester.domestic;

import nsu.oop.lab2.tester.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ArraysComparison {
    protected ArrayList<Integer> exceptionIndexes = new ArrayList<>();

    private boolean isArray(Object expected) {
        return expected != null && expected.getClass().isArray();
    }

    public void checkingForEqualsArrays(String message, Object expecteds, Object actuals) {
        internalOfComparisonArrays(message, expecteds, actuals, 0);
    }

    private void internalOfComparisonArrays(String message, Object expecteds, Object actuals, int curDeepOfRecursion) {
        if (expecteds == null) {
            Assert.fail(message + "expected array was null");
        }
        if (actuals == null) {
            Assert.fail(message + "actual array was null");
        }
        if (expecteds != actuals && !Arrays.deepEquals(new Object[]{expecteds}, new Object[]{actuals})) {
            String failureMessage = "";
            if (message != null && !message.isEmpty()) {
                failureMessage = message + ": ";
            }
            int actualsLength = Array.getLength(actuals);
            int expectedsLength = Array.getLength(expecteds);
            if (actualsLength != expectedsLength) {
                Assert.fail(failureMessage + "array lengths differed, expected.length=" + expectedsLength + " actual.length=" + actualsLength);
            }
            for (int i = 0; i < expectedsLength; ++i) {
                Object expected = Array.get(expecteds, i);
                Object actual = Array.get(actuals, i);
                if (curDeepOfRecursion + 1 > exceptionIndexes.size()) exceptionIndexes.add(i);
                else exceptionIndexes.set(curDeepOfRecursion, i);
                if (isArray(expected) && isArray(actual)) {
                    internalOfComparisonArrays(failureMessage, expected, actual, curDeepOfRecursion + 1);
                } else {
                    assertElementsEqual(expected, actual);
                }
            }
        }
    }

    protected abstract void assertElementsEqual(Object expected, Object actual);
}
