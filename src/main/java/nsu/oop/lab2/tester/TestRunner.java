package nsu.oop.lab2.tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import nsu.oop.lab2.tester.annotations.*;
import nsu.oop.lab2.tester.exceptions.TestRunnerException;

public class TestRunner {
    private static void printUsage() {
        System.out.println("""
                TestRunner did not get any class to run.
                Type list in args of TestRunner of classes from your application that could be checked by this test_framework.
                example: nsu.oop.lab2.app.ArraysTests nsu.oop.lab2.app.ObjectsTests""");
    }

    public static void main(String[] args) {
        if (args.length == 0) printUsage();
        else {
            for (String arg : args) {
                System.out.println("\nRunning " + arg);
                try {
                    final Class<?> testClass = Class.forName(arg);
                    runClassTesting(testClass);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void findAllPublicMethods(Method[] declaredMethods, List<Method> declaredTestMethods, List<Method> declaredBeforeMethods, List<Method> declaredBeforeClassMethods, List<Method> declaredAfterMethods, List<Method> declaredAfterClassMethods) {
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getAnnotation(BeforeClass.class) != null) {
                if (declaredMethod.getModifiers() == (Modifier.PRIVATE | Modifier.STATIC) || declaredMethod.getModifiers() == Modifier.PRIVATE) {
                    System.out.println("WARNING! \"" + declaredMethod.getName() + "\" is private BeforeClass-method! Make it public.");
                    continue;
                }
                declaredBeforeClassMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(Before.class) != null) {
                if (declaredMethod.getModifiers() == (Modifier.PRIVATE | Modifier.STATIC) || declaredMethod.getModifiers() == Modifier.PRIVATE) {
                    System.out.println("WARNING! \"" + declaredMethod.getName() + "\" is private Before-method! Make it public.");
                    continue;
                }
                declaredBeforeMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(Test.class) != null) {
                if (declaredMethod.getModifiers() == (Modifier.PRIVATE | Modifier.STATIC) || declaredMethod.getModifiers() == Modifier.PRIVATE) {
                    System.out.println("WARNING! \"" + declaredMethod.getName() + "\" is private Test-method! Make it public.");
                    continue;
                }
                declaredTestMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(After.class) != null) {
                if (declaredMethod.getModifiers() == (Modifier.PRIVATE | Modifier.STATIC) || declaredMethod.getModifiers() == Modifier.PRIVATE) {
                    System.out.println("WARNING! \"" + declaredMethod.getName() + "\" is private After-method! Make it public.");
                    continue;
                }
                declaredAfterMethods.add(declaredMethod);
            }
            if (declaredMethod.getAnnotation(AfterClass.class) != null) {
                if (declaredMethod.getModifiers() == (Modifier.PRIVATE | Modifier.STATIC) || declaredMethod.getModifiers() == Modifier.PRIVATE) {
                    System.out.println("WARNING! \"" + declaredMethod.getName() + "\" is private AfterClass-method! Make it public.");
                    continue;
                }
                declaredAfterClassMethods.add(declaredMethod);
            }
        }
    }

    private static void runBeforeClassMethods(Object constructorClass, List<Method> declaredBeforeClassMethods) {
        for (Method beforeClassMethod : declaredBeforeClassMethods) {
            try {
                beforeClassMethod.invoke(constructorClass);
            } catch (Exception e) {
                throw new TestRunnerException("Program failed at running of BeforeClass method \"" + beforeClassMethod.getName() + "\"", e);
            }
        }
    }

    private static void runBeforeMethods(Object constructorClass, Method[] declaredMethods, List<Method> declaredBeforeMethods, List<Method> declaredAfterCLassMethods) {
        for (Method beforeMethod : declaredBeforeMethods) {
            try {
                beforeMethod.invoke(constructorClass);
            } catch (Exception e) {
                System.err.println("Program failed at running of Before method \"" + beforeMethod.getName() + "\"");
                runAfterClassMethods(constructorClass,declaredAfterCLassMethods);
                throw new TestRunnerException("Program failed at running of Before method \"" + beforeMethod.getName() + "\"", e);
            }
        }
    }

    private static void runAfterMethods(Object constructorClass, List<Method> declaredAfterMethods) {
        for (Method afterMethod : declaredAfterMethods) {
            try {
                afterMethod.invoke(constructorClass);
            } catch (Exception e) {
                System.err.println("Cause of exception in invoke of after method " + afterMethod.getName() + ":  " + e.getCause());
            }
        }
    }

    private static void runAfterClassMethods(Object constructorClass, List<Method> declaredAfterClassMethods) {
        for (Method afterClassMethod : declaredAfterClassMethods) {
            try {
                afterClassMethod.invoke(constructorClass);
            } catch (Exception e) {
                System.err.println("Cause of exception in invoke of afterClass method " + afterClassMethod.getName() + ":  " + e.getCause());
            }
        }
    }

    public static void runClassTesting(Class<?> testClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Method> declaredTestMethods = new ArrayList<>();
        List<Method> declaredBeforeMethods = new ArrayList<>();
        List<Method> declaredBeforeClassMethods = new ArrayList<>();
        List<Method> declaredAfterMethods = new ArrayList<>();
        List<Method> declaredAfterClassMethods = new ArrayList<>();
        Method[] declaredMethods = testClass.getDeclaredMethods();
        Constructor<?> constructor = testClass.getConstructor();
        Object constructorClass = constructor.newInstance();
        findAllPublicMethods(declaredMethods, declaredTestMethods, declaredBeforeMethods, declaredBeforeClassMethods, declaredAfterMethods, declaredAfterClassMethods);
        long countOfTests = declaredTestMethods.size();
        long countOfFailedTests = 0;
        long numberOfTest = 0;
        if (countOfTests != 0) {
            try {
                runBeforeClassMethods(constructorClass, declaredBeforeClassMethods);
            } catch (TestRunnerException e) {
                System.err.println("Cause of exception in invoke of BeforeClass method: " + e.getCause());
                throw new TestRunnerException("TestClass failed. BeforeClass Method was broken",e);
            }

        }
        for (Method testMethod : declaredTestMethods) {
            numberOfTest++;
            try{
                runBeforeMethods(constructorClass, declaredMethods, declaredBeforeMethods, declaredAfterClassMethods);
            } catch (TestRunnerException e){
                System.err.println("Cause of exception in invoke of Before method: " + e.getCause());
                throw new TestRunnerException("TestClass failed. Before Method was broken.",e);
            }
            boolean gotException = false;
            try {
                testMethod.invoke(constructorClass);
            } catch (Exception e) {
                gotException = true;
                Class<?> actual = e.getCause().getClass();
                Class<?> expected = testMethod.getAnnotation(Test.class).expected();
                if (!actual.equals(expected)) {
                    countOfFailedTests++;
                    System.err.println("Expected exception " + expected.getName() + "but was " + actual.getName());
                    e.printStackTrace();
                }
            } finally {
                runAfterMethods(constructorClass, declaredAfterMethods);
                if (numberOfTest == countOfTests) runAfterClassMethods(constructorClass, declaredAfterClassMethods);
            }
            if (!gotException && testMethod.getAnnotation(Test.class).expected() != Test.None.class) {
                System.err.println("Test did not passed. Expected " +
                        testMethod.getAnnotation(Test.class).expected() + " but there are not any exception!");
                countOfFailedTests++;
            }
        }
        if (countOfFailedTests == 0) {
            System.out.println("All tests passed: " + countOfTests + " of " + countOfTests + " tests");
        } else {
            System.out.println("Tests failed: " + countOfFailedTests + " of " + countOfTests + " tests");
        }
    }
}