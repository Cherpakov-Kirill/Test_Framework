package nsu.oop.lab2.tester.exceptions;

public class TestRunnerException extends RuntimeException{
    public TestRunnerException(String message, Throwable cause) {
        super("TestRunner failed: " + message, cause);
    }

}
