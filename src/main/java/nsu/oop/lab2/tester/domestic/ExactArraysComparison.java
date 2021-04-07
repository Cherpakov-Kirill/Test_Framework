package nsu.oop.lab2.tester.domestic;

import nsu.oop.lab2.tester.Assert;

public class ExactArraysComparison extends ArraysComparison {
    // CR: redundant, you have this ctor by default
    public ExactArraysComparison() {
    }

    @Override
    protected void assertElementsEqual(Object expected, Object actual) {
        StringBuilder outMessage = new StringBuilder();
        // CR: you construct this message for each pair of elements even if they are equal
        outMessage.append("Actual array differs from expected array is the first time at element ");
        for (Integer curIndex : exceptionIndexes) outMessage.append("[").append(curIndex).append("]");
        Assert.assertEquals(outMessage.toString(), expected, actual);
    }
}
