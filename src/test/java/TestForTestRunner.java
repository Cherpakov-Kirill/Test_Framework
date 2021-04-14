import nsu.oop.lab2.tester.TestRunner;
import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class TestForTestRunner {
    @Test
    public void allAnnotation() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        String testName = "AllAnnotation";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> allAnnotation = Class.forName(testName, true, classLoader);

        TestRunner.runTest(allAnnotation, exception -> {
            throw new RuntimeException(exception);
        });
    }

    @Test
    public void PrivateBeforeClassMethod() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        String testName = "PrivateBeforeClassMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateBefore = Class.forName(testName, true, classLoader);

        TestRunner.runTest(privateBefore, exception -> {
            throw new RuntimeException(exception);
        });
    }

    @Test
    public void PrivateAfterClassMethod() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        String testName = "PrivateAfterClassMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateAfterClass = Class.forName(testName, true, classLoader);

        TestRunner.runTest(privateAfterClass, exception -> {
            throw new RuntimeException(exception);
        });
    }

    @Test
    public void privateAfterMethod() throws ClassNotFoundException, MalformedURLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String testName = "PrivateAfterMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateAfter = Class.forName(testName, true, classLoader);
        TestRunner.runTest(privateAfter, exception -> {
            throw new RuntimeException(exception);
        });
    }

    @Test
    public void privateBeforeMethod() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        String testName = "PrivateBeforeMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateBefore = Class.forName(testName, true, classLoader);

        TestRunner.runTest(privateBefore, exception -> {
            throw new RuntimeException(exception);
        });
    }

    @Test
    public void privateTestMethod() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        String testName = "PrivateTestMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateTest = Class.forName(testName, true, classLoader);

        TestRunner.runTest(privateTest, exception -> {
            throw new RuntimeException(exception);
        });
    }

    @Test
    public void PrivateAfterAndBeforeMethods() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        String testName = "PrivateAfterAndBeforeMethods";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateAfterAndBefore = Class.forName(testName, true, classLoader);

        TestRunner.runTest(privateAfterAndBefore, exception -> {
            throw new RuntimeException(exception);
        });
    }
}
