package nsu.oop.lab2.tester.exceptions;

public class AssertException extends RuntimeException {
    private final String exceptionMessage;

    public AssertException(String message) {
        exceptionMessage = message;
    }

    public AssertException() {
        exceptionMessage = "";
    }

    public String toString() {
        return exceptionMessage;
    }
}
