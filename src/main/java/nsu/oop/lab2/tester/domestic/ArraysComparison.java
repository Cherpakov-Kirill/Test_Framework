// CR: domestic? probably comparison is better name
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

    // CR: I think it's better to cast somewhere to arrays
    public void checkingForEqualsArrays(String message, Object expecteds, Object actuals) {
        internalOfComparisonArrays(message, expecteds, actuals, 0);
    }

    // CR: methods are usually named with verbs
    private void internalOfComparisonArrays(String message, Object expecteds, Object actuals, int curDeepOfRecursion) {
        // CR: it's possible only once in recursion
        if (expecteds == null) {
            Assert.fail(message + "expected array was null");
        }
        if (actuals == null) {
            Assert.fail(message + "actual array was null");
        }
        // CR: additional wrap
        if (expecteds != actuals && !Arrays.deepEquals(new Object[]{expecteds}, new Object[]{actuals})) {
            String failureMessage = "";
            if (message != null && !message.isEmpty()) {
                failureMessage = message + ": ";
            }
            int actualsLength = Array.getLength(actuals);
            int expectedsLength = Array.getLength(expecteds);
            if (actualsLength != expectedsLength) {
                // CR: probably it's good to print which arrays exactly or at least current deepness
                Assert.fail(failureMessage + "array lengths differed, expected.length=" + expectedsLength + " actual.length=" + actualsLength);
            }
            for (int i = 0; i < expectedsLength; ++i) {
                // CR: better use actuals.length, it's typed
                Object expected = Array.get(expecteds, i);
                Object actual = Array.get(actuals, i);
                if (curDeepOfRecursion + 1 > exceptionIndexes.size()) exceptionIndexes.add(i);
                // CR: definitely can be optimised, lots of redundant array writes and rewrites
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
