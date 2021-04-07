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
    public void privateAfterMethod() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String testName = "PrivateAfterMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateAfter = Class.forName(testName, true, classLoader);

        TestRunner.runClassTesting(privateAfter);
    }

    @Test
    public void privateBeforeMethod() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String testName = "PrivateBeforeMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateBefore = Class.forName(testName, true, classLoader);

        TestRunner.runClassTesting(privateBefore);
    }

    @Test
    public void privateTestMethod() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String testName = "PrivateTestMethod";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> privateTest = Class.forName(testName, true, classLoader);

        // CR: i'm not sure what your tests actually do. in your program if test actually throws exception it won't be
        // CR: thrown here, so you just running some tests without any checking
        TestRunner.runClassTesting(privateTest);
    }

    @Test
    public void PrivateAfterAndBeforeMethods() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String testName = "PrivateAfterAndBeforeMethods";
        System.out.println("---" + testName + "---");

        File testDataDir = new File("dataForTestRunnerTesting");
        File fileToLoad = new File("dataForTestRunnerTesting/" + testName + ".java");

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        javaCompiler.run(null, null, null, fileToLoad.toString());

        URLClassLoader classLoader = new URLClassLoader(new URL[]{testDataDir.toURI().toURL()});
        Class<?> PrivateAfterAndBefore = Class.forName(testName, true, classLoader);

        TestRunner.runClassTesting(PrivateAfterAndBefore);
    }
}
