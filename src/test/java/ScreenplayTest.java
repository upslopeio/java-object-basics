import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.ArrayList;

import static io.upslope.TestHelpers.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class ScreenplayTest {

    @Test
    void hasTitleGetterAndSetter() {
        String randomTitle = getRandomString();

        testGetterAndSetter("Screenplay", "title", String.class, randomTitle);
    }

    @Test
    void hasLines() {
        String className = "Screenplay";
        String fieldName = "lines";
        String accessor = "generate";
        String mutator = "addLine";

        Class<?> c = getaClass(className);
        Object instance = getInstance(className, c);
        Field lines = assertArrayListField(className, fieldName, c);
        Class<?> parameterType = String.class;

        assertArrayListMutator(className, fieldName, mutator, c, instance, lines, parameterType);

        String randomTitle = getRandomString();
        String setterMethodName = "setTitle";
        Class<String> setterParameterType = String.class;
        invokeSetter(className, c, instance, randomTitle, setterMethodName, setterParameterType);

        Method accessorMethod = getAccessorMethod(className, accessor, c, parameterType);

        try {
            String result = (String) accessorMethod.invoke(instance);
            assertLinesMatch(
                    asList(randomTitle, "foo", "bar"),
                    asList(result.split("\n")),
                    "Expected Screenplay generate() to include the title and all of the lines, separated by newlines"
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            fail(String.format("Could not call method %s() on %s", accessor, className));
        }
    }

}
