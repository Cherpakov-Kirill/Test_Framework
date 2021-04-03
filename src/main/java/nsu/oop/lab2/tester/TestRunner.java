package nsu.oop.lab2.tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import nsu.oop.lab2.tester.annotations.After;
import nsu.oop.lab2.tester.annotations.Before;
import nsu.oop.lab2.tester.annotations.Test;

public class TestRunner {
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println("\nRunning " + arg);
            try {
                final Class<?> testClass = Class.forName(arg);
                runClassTesting(testClass);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runClassTesting(Class<?> testClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Method[] declaredMethods = testClass.getDeclaredMethods();
        final Constructor<?> constructor = testClass.getDeclaredConstructors()[0];
        final Object constructorClass = constructor.newInstance();
        ArrayList<Method> declaredTestMethods = new ArrayList<>();
        ArrayList<Method> declaredBeforeMethods = new ArrayList<>();
        ArrayList<Method> declaredAfterMethods = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getAnnotation(Before.class) != null) declaredBeforeMethods.add(declaredMethod);
            if (declaredMethod.getAnnotation(Test.class) != null) declaredTestMethods.add(declaredMethod);
            if (declaredMethod.getAnnotation(After.class) != null) declaredAfterMethods.add(declaredMethod);
        }
        long countOfTests = declaredTestMethods.size();
        long countOfFalseTests = 0;
        for (Method testMethod : declaredTestMethods) {
            if (testMethod.getModifiers() == Modifier.PRIVATE) {
                System.out.println("WARNING! \"" + testMethod.getName() + "\" is private Test-method! Make it public.");
                continue;
            }
            for (Method beforeMethod : declaredBeforeMethods) {
                if (beforeMethod.getModifiers() == Modifier.PRIVATE) {
                    System.out.println("WARNING! \"" + beforeMethod.getName() + "\" is private Before-method! Make it public.");
                    continue;
                }
                try {
                    beforeMethod.invoke(constructorClass);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            boolean gotException = false;
            try {
                testMethod.invoke(constructorClass);
            } catch (InvocationTargetException e) {
                gotException = true;
                Class<?> target = e.getTargetException().getClass();
                Class<?> expected = testMethod.getAnnotation(Test.class).expected();
                if (!target.equals(expected)) {
                    countOfFalseTests++;
                    e.getTargetException().printStackTrace();
                }
            }
            if (!gotException && testMethod.getAnnotation(Test.class).expected() != Test.None.class) {
                System.err.println("Test did not passed. Expected " +
                        testMethod.getAnnotation(Test.class).expected() + " but there are not any exception!");
                countOfFalseTests++;
            }
            for (Method afterMethod : declaredAfterMethods) {
                if (afterMethod.getModifiers() == Modifier.PRIVATE) {
                    System.out.println("WARNING! \"" + afterMethod.getName() + "\" is private After-method! Make it public.");
                    continue;
                }
                try {
                    afterMethod.invoke(constructorClass);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (countOfFalseTests == 0) {
            System.out.println("All tests passed: " + countOfTests + " of " + countOfTests + " tests");
        } else {
            System.out.println("Tests failed: " + countOfFalseTests + " of " + countOfTests + " tests");
        }
    }


}
