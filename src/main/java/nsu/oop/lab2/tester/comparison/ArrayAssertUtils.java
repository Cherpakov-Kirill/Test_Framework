package nsu.oop.lab2.tester.comparison;

import nsu.oop.lab2.tester.exceptions.AssertException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayAssertUtils {
    public static void arrayEquals(String message, Object[] expecteds, Object[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, boolean[] expecteds, boolean[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, byte[] expecteds, byte[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, char[] expecteds, char[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, short[] expecteds, short[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, int[] expecteds, int[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, long[] expecteds, long[] actuals) {
        (new ExactArraysComparison()).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, double[] expecteds, double[] actuals, double delta) {
        (new InexactArraysComparison(delta)).checkingForEqualsArrays(message, expecteds, actuals);
    }

    public static void arrayEquals(String message, float[] expecteds, float[] actuals, double delta) {
        (new InexactArraysComparison((float) delta)).checkingForEqualsArrays(message, expecteds, actuals);
    }


    private static abstract class ArraysComparison {
        protected final List<Integer> exceptionIndexes = new ArrayList<>();
        protected boolean elementsAreEqual;

        protected void checkingForEqualsArrays(String message, Object expecteds, Object actuals) {
            if (expecteds == null) {
                throw new AssertException(message + "expected array was null");
            }
            if (actuals == null) {
                throw new AssertException(message + "actual array was null");
            }
            if (!expecteds.getClass().isArray()) throw new IllegalArgumentException("Expected-object is not array");
            if (!actuals.getClass().isArray()) throw new IllegalArgumentException("Actual-object is not array");
            compareArrays(message, expecteds, actuals, 0);
            StringBuilder outMessage = new StringBuilder();
            if(!elementsAreEqual){
                outMessage.append("Actual array differs from expected array is the first time at element ");
                for (Integer curIndex : exceptionIndexes) outMessage.append("[").append(curIndex).append("]");
                throw new AssertException(outMessage.toString());
            }
        }

        private boolean isArray(Object expected) {
            return expected != null && expected.getClass().isArray();
        }

        private void compareArrays(String message, Object expecteds, Object actuals, int curDeepOfRecursion) {
            String failureMessage = "";
            if (message != null && !message.isEmpty()) {
                failureMessage = message + ": ";
            }
            int actualsLength = Array.getLength(actuals);
            int expectedsLength = Array.getLength(expecteds);
            if (actualsLength != expectedsLength) {
                throw new AssertException(failureMessage + "array lengths differed on deep " + curDeepOfRecursion + ", expected.length=" + expectedsLength + " actual.length=" + actualsLength);
            }
            for (int i = 0; i < expectedsLength; ++i) {
                Object expected = Array.get(expecteds, i);
                Object actual = Array.get(actuals, i);
                if (isArray(expected) && isArray(actual)) {
                    compareArrays(failureMessage, expected, actual, curDeepOfRecursion + 1);
                } else {
                    compareElements(expected, actual);
                }
                if (!elementsAreEqual){
                    exceptionIndexes.add(0, i);
                    return;
                }
            }
        }

        protected abstract void compareElements(Object expected, Object actual);
    }

    private static class ExactArraysComparison extends ArraysComparison {
        @Override
        protected void compareElements(Object expected, Object actual) {
            if (expected == actual) elementsAreEqual = true;
            elementsAreEqual = expected.equals(actual);
        }
    }

    public static class InexactArraysComparison extends ArraysComparison {
        Object delta;

        public InexactArraysComparison(double delta) {
            this.delta = delta;
        }

        public InexactArraysComparison(float delta) {
            this.delta = delta;
        }

        @Override
        protected void compareElements(Object expected, Object actual) {
            if (expected instanceof Double)
                elementsAreEqual = (Math.abs((Double) expected - (Double) actual) <= (Double) delta);
            else elementsAreEqual = (Math.abs((Float) expected - (Float) actual) <= (Float) delta);
        }
    }
}
