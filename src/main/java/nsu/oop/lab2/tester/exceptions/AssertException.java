package nsu.oop.lab2.tester.exceptions;

public class AssertException extends RuntimeException {
    public AssertException(String message) {
        super("Assert failed: " + message);
    }
}
