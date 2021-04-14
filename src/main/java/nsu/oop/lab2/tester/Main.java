package nsu.oop.lab2.tester;

public class Main {
    public static void main(String[] args) {
        Class<?> c = Class.forName(args[0]);
        TestRunner testRunner = new TestRunner(c);
        testRunner.runClassTesting();
    }
}
