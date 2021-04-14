package nsu.oop.lab2.tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import nsu.oop.lab2.tester.annotations.*;
import nsu.oop.lab2.tester.exceptions.TestRunnerException;

public class TestRunner {
    private static void printUsage() {
        System.out.println("""
                TestRunner did not get any class to run.
                Type list in args of TestRunner of classes from your application that could be checked by this test_framework.
                example: nsu.oop.lab2.app.ArraysTests nsu.oop.lab2.app.ObjectsTests""");
    }

    private final Consumer<Exception> exceptionHandler;

    TestRunner() {
        exceptionHandler = TestRunner::printError;
    }

    TestRunner(Consumer<Exception> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public static void runTest(Class<?> testClass) {
        TestRunner testRunner = new TestRunner(testClass, TestRunner::printError);
        testRunner.runClassTesting();
    }

    public static void runTest(Class<?> testClass, Consumer<Exception> exceptionHandler) {
        TestRunner testRunner = new TestRunner(testClass, exceptionHandler);
        testRunner.runClassTesting();
    }

    private static void printError(Exception e) {
        if (e instanceof InvocationTargetException) {
            // TODO: print user error
            return;
        }
        // TDOO: print framework error
    }

    public static void main(String[] args) {
        if (args.length == 0) printUsage();
        else {
            for (String arg : args) {
                System.out.println("\nRunning " + arg);
                try {
                    final Class<?> testClass = Class.forName(arg);
                    TestRunner testRunner = new TestRunner();
                    testRunner.runClassTesting(testClass);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<Method> collectMethods(Method[] declaredMethods, Class<? extends Annotation> toSearch) {
        for (Method declaredMethod : declaredMethods) {
            declaredMethod.getAnnotation(toSearch);
        }
    }

    private static void invalidMethod(String annoName) {

    }

    private static void findAllPublicMethods(Method[] declaredMethods, List<Method> declaredTestMethods, List<Method> declaredBeforeMethods, List<Method> declaredBeforeClassMethods, List<Method> declaredAfterMethods, List<Method> declaredAfterClassMethods) {
        collectMethods(declaredMethods, BeforeClass.class);
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
                int modifiers = declaredMethod.getModifiers();
                if ((!Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers))) {
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

    private static void runBeforeClassMethods(Object constructorClass, List<Method> declaredBeforeClassMethods) throws InvocationTargetException {
        for (Method beforeClassMethod : declaredBeforeClassMethods) {
            try {
                beforeClassMethod.invoke(constructorClass);
            }  catch (InvocationTargetException e) {
                System.err.println("");
                throw e;
            }
            catch (Exception e) {
                throw new TestRunnerException("Program failed at running of BeforeClass method \"" + beforeClassMethod.getName() + "\"", e);
            }
        }
    }

    private boolean runMethods(List<Method> declaredBeforeMethods) {
        for (Method beforeMethod : declaredBeforeMethods) {
            try {
                beforeMethod.invoke(constructorClass);
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return false;
            }
        }
        return true;
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

    public void runClassTesting(Class<?> testClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] declaredMethods = testClass.getDeclaredMethods();
        List<Method> declaredTestMethods = collectMethods(declaredMethods, Test.class);
        List<Method> declaredBeforeMethods = new ArrayList<>();
        List<Method> declaredBeforeClassMethods = new ArrayList<>();
        List<Method> declaredAfterMethods = new ArrayList<>();
        List<Method> declaredAfterClassMethods = new ArrayList<>();
        Constructor<?> constructor = testClass.getConstructor();
        Object constructorClass = constructor.newInstance();
        findAllPublicMethods(declaredMethods, declaredTestMethods, declaredBeforeMethods, declaredBeforeClassMethods, declaredAfterMethods, declaredAfterClassMethods);
        long countOfTests = declaredTestMethods.size();
        long countOfFailedTests = 0;
        long numberOfTest = 0;
        if (!runMethods(declaredBeforeClassMethods)) return;
        for (Method testMethod : declaredTestMethods) {
            numberOfTest++;
            if (!runMethods(constructorClass, declaredMethods, declaredBeforeMethods)) {
                runMethods(constructorClass, declaredMethods, declaredAfterClassMethods);
                return;
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
                runMethods(declaredAfterClassMethods);
                if (numberOfTest == countOfTests) runAfterClassMethods(constructorClass, declaredAfterClassMethods);
            }
            if (!gotException && testMethod.getAnnotation(Test.class).expected() != Test.None.class) {
                System.err.println("Test did not passed. Expected " +
                        testMethod.getAnnotation(Test.class).expected() + " but there are not any exception!");
                countOfFailedTests++;
            }
        }
        runMethods(declaredAfterClassMethods);
        if (countOfFailedTests == 0) {
            System.out.println("All tests passed: " + countOfTests + " of " + countOfTests + " tests");
        } else {
            System.out.println("Tests failed: " + countOfFailedTests + " of " + countOfTests + " tests");
        }
    }
}