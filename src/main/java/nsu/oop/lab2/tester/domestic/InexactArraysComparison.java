package nsu.oop.lab2.tester.domestic;

import nsu.oop.lab2.tester.Assert;

public class InexactArraysComparison extends ArraysComparison {
    Object delta;

    public InexactArraysComparison(double delta) {
        this.delta = delta;
    }

    public InexactArraysComparison(float delta) {
        this.delta = delta;
    }

    @Override
    protected void assertElementsEqual(Object expected, Object actual) {
        StringBuilder outMessage = new StringBuilder();
        outMessage.append("Actual array differs from expected array is the first time at element ");
        for (Integer curIndex : exceptionIndexes) outMessage.append("[").append(curIndex).append("]");
        if (expected instanceof Double) {
            Assert.assertEquals(outMessage.toString(), (Double) expected, (Double) actual, (Double) delta);
        } else {
            Assert.assertEquals(outMessage.toString(), (Float) expected, (Float) actual, (Float) delta);
        }
    }
}
