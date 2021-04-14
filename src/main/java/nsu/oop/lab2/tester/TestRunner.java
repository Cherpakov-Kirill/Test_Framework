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

public class TestRunner {
    private Object constructorClass;

    private final Consumer<Exception> exceptionHandler;

    TestRunner() {
        exceptionHandler = TestRunner::printError;
    }

    TestRunner(Consumer<Exception> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public static void runTest(Class<?> testClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestRunner testRunner = new TestRunner();
        testRunner.runClassTesting(testClass);
    }

    public static void runTest(Class<?> testClass, Consumer<Exception> exceptionHandler) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestRunner testRunner = new TestRunner(exceptionHandler);
        testRunner.runClassTesting(testClass);
    }

    private static void printError(Exception e) {
        if (e instanceof InvocationTargetException) {
            System.err.println("User's fail: Cause of exception in invoke of method: " + e.getCause());
            return;
        }
        System.err.println("TestFramework fail!" + e.getCause());
    }

    private List<Method> collectMethods(Method[] declaredMethods, Class<? extends Annotation> toSearch) {
        List<Method> toSearchMethods = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getAnnotation(toSearch) != null) {
                int modifiers = declaredMethod.getModifiers();
                if ((!Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers))) {
                    System.out.println("WARNING! \"" + declaredMethod.getName() + "\" is private " + toSearch.getSimpleName() + "-method! Make it public.");
                    continue;
                }
                toSearchMethods.add(declaredMethod);
            }
        }
        return toSearchMethods;
    }

    private boolean runMethods(List<Method> declaredMethods) {
        for (Method beforeMethod : declaredMethods) {
            try {
                beforeMethod.invoke(constructorClass);
            } catch (Exception e) {
                System.err.println(beforeMethod.getName() + " was broken!");
                exceptionHandler.accept(e);
                return false;
            }
        }
        return true;
    }

    private void runClassTesting(Class<?> testClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] declaredMethods = testClass.getDeclaredMethods();
        List<Method> declaredTestMethods = collectMethods(declaredMethods, Test.class);
        List<Method> declaredBeforeMethods = collectMethods(declaredMethods, Before.class);
        List<Method> declaredBeforeClassMethods = collectMethods(declaredMethods, BeforeClass.class);
        List<Method> declaredAfterMethods = collectMethods(declaredMethods, After.class);
        List<Method> declaredAfterClassMethods = collectMethods(declaredMethods, AfterClass.class);
        Constructor<?> constructor = testClass.getConstructor();
        constructorClass = constructor.newInstance();
        long countOfTests = declaredTestMethods.size();
        long countOfFailedTests = 0;
        long numberOfTest = 0;
        if (!runMethods(declaredBeforeClassMethods)) return;
        for (Method testMethod : declaredTestMethods) {
            numberOfTest++;
            if (!runMethods(declaredBeforeMethods)) {
                runMethods(declaredAfterClassMethods);
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
                runMethods(declaredAfterMethods);
                if (numberOfTest == countOfTests) runMethods(declaredAfterClassMethods);
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