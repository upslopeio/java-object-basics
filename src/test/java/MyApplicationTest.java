import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class MyApplicationTest {

    @Test
    void printsSomethingFromPublicStaticVoidMain() {
        Class<?> mainClass = null;
        try {
            mainClass = Class.forName("MyApplication");
        } catch (ClassNotFoundException e) {
            fail(String.format("Expected to find a class named `%s` but found nothing", "MyApplication"));
        }

        try {
            Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
            Class<?> fieldType = mainMethod.getReturnType();
            assertEquals(Void.TYPE, fieldType, "expected MyApplication.main() to return void");

            int modifiers = mainMethod.getModifiers();
            if (!Modifier.isPublic(modifiers)) {
                fail("Expected MyApplication.main() to be public");
            }

            if (!Modifier.isStatic(modifiers)) {
                fail("Expected MyApplication.main() to be static");
            }

            String output = capturePrintStream(() -> {
                try {
                    mainMethod.invoke(null, (Object) null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            assertFalse(output.isEmpty(), "Expected MyApplication.main to print something to System.out");

        } catch (NoSuchMethodException e) {
            fail("Expected MyApplication to have a method named main");
        }
    }

    private String capturePrintStream(Runnable fn) {
        PrintStream original = System.out;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(stream);
            System.setOut(ps);
            fn.run();
            return stream.toString();
        } finally {
            System.setOut(original);
        }
    }

}
