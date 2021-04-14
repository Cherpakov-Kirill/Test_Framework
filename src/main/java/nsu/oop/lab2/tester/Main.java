package nsu.oop.lab2.tester;

public class Main {
    private static void printUsage() {
        System.out.println("""
                TestRunner did not get any class to run.
                Type list in args of TestRunner of classes from your application that could be checked by this test_framework.
                example: nsu.oop.lab2.app.ArraysTests nsu.oop.lab2.app.ObjectsTests""");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
        } else {
            for (String arg : args) {
                System.out.println("\nRunning " + arg);
                try {
                    final Class<?> testClass = Class.forName(arg);
                    TestRunner.runTest(testClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
