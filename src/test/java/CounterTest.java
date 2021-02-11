import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.upslope.TestHelpers.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    @Test
    void countsThings() {
        String className = "Counter";
        String fieldName = "clicks";
        String increment = "increment";
        String decrement = "decrement";
        String getClicks = "getClicks";

        Class<?> counterClass = getaClass(className);
        Object instance = getInstance(className, counterClass);
        Field clicks = assertPrimitiveField(className, fieldName, counterClass, int.class);
        clicks.setAccessible(true);

        try {
            Method incrementMethod = counterClass.getDeclaredMethod(increment);
            incrementMethod.invoke(instance);
            incrementMethod.invoke(instance);
            incrementMethod.invoke(instance);
            assertEquals(3, clicks.get(instance), "Expected increment() to increase clicks by 1");
        } catch (NoSuchMethodException e) {
            fail(format(
                    "Expected %s to have a method named %s",
                    className,
                    increment
            ));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail(
                    "There was an error calling increment() on Counter"
            );
        }

        try {
            Method decrementMethod = counterClass.getDeclaredMethod(decrement);
            decrementMethod.invoke(instance);
            assertEquals(2, clicks.get(instance), "Expected decrement() to decrease clicks by 1");
        } catch (NoSuchMethodException e) {
            fail(format(
                    "Expected %s to have a method named %s",
                    className,
                    decrement
            ));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail(
                    "There was an error calling decrement() on Counter"
            );
        }

        try {
            Method getClicksMethod = counterClass.getDeclaredMethod(getClicks);
            int clicksResult = (int) getClicksMethod.invoke(instance);
            assertEquals(2, clicksResult, "Expected getClicks() to return the number of clicks");
        } catch (NoSuchMethodException e) {
            fail(format(
                    "Expected %s to have a method named %s",
                    className,
                    getClicks
            ));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail(
                    "There was an error calling getClicks() on Counter"
            );
        }
    }

}
