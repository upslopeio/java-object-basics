import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static io.upslope.TestHelpers.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.fail;

class FormFieldsTest {

    @Test
    void hasFormFields() {
        String className = "FormFields";
        String fieldName = "fields";
        String accessor = "urlEncoded";
        String mutator = "addField";

        Class<?> theClass = getaClass(className);
        Object instance = getInstance(className, theClass);
        Field privateField = assertMapField(className, fieldName, theClass);
        Class<?> keyType = String.class;
        Class<?> valueType = String.class;

        assertMapMutator(className, fieldName, mutator, theClass, instance, privateField, HashMap.class, keyType, valueType);

//        String randomTitle = getRandomString();
//        String setterMethodName = "setTitle";
//        Class<String> setterParameterType = String.class;
//        invokeSetter(className, c, instance, randomTitle, setterMethodName, setterParameterType);
//
//        Method accessorMethod = getAccessorMethod(className, accessor, c, parameterType);
//
//        try {
//            String result = (String) accessorMethod.invoke(instance);
//            assertLinesMatch(
//                    asList(randomTitle, "foo", "bar"),
//                    asList(result.split("\n")),
//                    "Expected Screenplay generate() to include the title and all of the lines, separated by newlines"
//            );
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            fail(String.format("Could not call method %s() on %s", accessor, className));
//        }
    }

}
