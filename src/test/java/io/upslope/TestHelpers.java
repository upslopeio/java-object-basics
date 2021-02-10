package io.upslope;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class TestHelpers {

    // https://stackoverflow.com/a/59038893
    public static <C> Class<C[]> arrayClass(Class<C> klass) {
        return (Class<C[]>) Array.newInstance(klass, 0).getClass();
    }

    public static Class<?> objectiveClass(Class<?> klass) {
        Class<?> component = klass.getComponentType();
        if (component != null) {
            if (component.isPrimitive() || component.isArray())
                return arrayClass(objectiveClass(component));
        } else if (klass.isPrimitive()) {
            if (klass == char.class)
                return Character.class;
            if (klass == int.class)
                return Integer.class;
            if (klass == boolean.class)
                return Boolean.class;
            if (klass == byte.class)
                return Byte.class;
            if (klass == double.class)
                return Double.class;
            if (klass == float.class)
                return Float.class;
            if (klass == long.class)
                return Long.class;
            if (klass == short.class)
                return Short.class;
        }

        return klass;
    }

    public static void testGetterAndSetter(String className, String fieldName, Class<?> parameterType, Object value) {
        String capitalized = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1).toLowerCase();
        String getter = String.format("get%s", capitalized);
        String setter = String.format("set%s", capitalized);
        Class c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail(String.format("Expected to find a class named `%s` but found nothing", className));
        }

        Object instance = null;
        try {
            instance = c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            fail(String.format("Call to `new %s()` failed", className));
        }

        try {
            Field declaredField = c.getDeclaredField(fieldName);
            Class<?> fieldType = declaredField.getType();
            assertEquals(parameterType, fieldType, String.format("Expected %s to be of type %s but instead it is of type %s", fieldName, parameterType, fieldType));

            int modifiers = declaredField.getModifiers();
            assertTrue(Modifier.isPrivate(modifiers), String.format("Expected the %s field to be private but it is not", fieldName));
        } catch (NoSuchFieldException e) {
            fail(String.format("Expected %s to have a private field named %s but it does not", className, fieldName));
        }

        Method setIdMethod = null;
        try {
            setIdMethod = c.getDeclaredMethod(setter, parameterType);
            Class<?> returnType = setIdMethod.getReturnType();
            assertEquals(Void.TYPE, returnType, String.format("Expected %s() to return void but it returns %s", setter, returnType));
            setIdMethod.invoke(instance, value);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            fail(String.format("Could not find a public %s(%s) method on %s", setter, parameterType, className));
        } catch (InvocationTargetException e) {
            fail(String.format("Could not call %s(%s) on %s", setter, parameterType, className));
        }

        Method getIdMethod = null;
        try {
            getIdMethod = c.getDeclaredMethod(getter);
            Class<?> returnType = getIdMethod.getReturnType();
            assertEquals(parameterType, returnType, String.format("Expected %s() to return %s but it returns %s", getter, parameterType, returnType));
        } catch (NoSuchMethodException e) {
            fail(String.format("Could not find method %s() on %s", getter, className));
        }
        try {
            assertEquals(
                    getIdMethod.invoke(instance),
                    value,
                    String.format("Expected %s() to return the value passed to %s()", getter, setter)
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            fail(String.format("Could not call method %s() on %s", getter, className));
        }
    }

    public static Method getAccessorMethod(String className, String accessor, Class<?> c, Class<?> parameterType) {
        Method accessorMethod = null;
        try {
            accessorMethod = c.getDeclaredMethod(accessor);
            Class<?> returnType = accessorMethod.getReturnType();
            assertEquals(
                    parameterType,
                    returnType,
                    format("Expected %s() to return %s but it returns %s", accessor, parameterType, returnType)
            );
        } catch (NoSuchMethodException e) {
            fail(format("Could not find method %s() on %s", accessor, className));
        }
        return accessorMethod;
    }

    public static void invokeSetter(String className, Class<?> c, Object instance, String randomTitle, String setterMethodName, Class<String> setterParameterType) {
        Method setTitleMethod;
        try {
            setTitleMethod = c.getDeclaredMethod(setterMethodName, setterParameterType);
            setTitleMethod.invoke(instance, randomTitle);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            fail(format(
                    "Could not find a public %s(%s) method on %s",
                    setterMethodName,
                    setterParameterType.getSimpleName(), className)
            );
        } catch (InvocationTargetException e) {
            fail(format(
                    "Could not call %s(%s) on %s",
                    setterMethodName,
                    setterParameterType.getSimpleName(),
                    className
            ));
        }
    }

    public static void assertArrayListMutator(String className, String fieldName, String mutator, Class<?> c, Object instance, Field lines, Class<?> parameterType) {
        Method mutatorMethod = null;
        try {
            mutatorMethod = c.getDeclaredMethod(mutator, parameterType);
            Class<?> returnType = mutatorMethod.getReturnType();
            assertEquals(
                    Void.TYPE,
                    returnType,
                    format("Expected %s() to return void but it returns %s", mutator, returnType)
            );
            mutatorMethod.invoke(instance, "foo");
            mutatorMethod.invoke(instance, "bar");

            lines.setAccessible(true);
            ArrayList<String> data = (ArrayList<String>) lines.get(instance);
            assertEquals(
                    asList("foo", "bar"),
                    data,
                    format("Expected the ArrayList %s to contain all the items passed to %s", fieldName, mutator)
            );
        } catch (NoSuchMethodException | IllegalAccessException e) {
            fail(format(
                    "Could not find a public %s(%s) method on %s",
                    mutator,
                    parameterType.getSimpleName(),
                    className
            ));
        } catch (InvocationTargetException e) {
            fail(format("Could not call %s(%s) on %s", mutator, parameterType, className));
        }
    }

    public static Field assertArrayListField(String className, String fieldName, Class<?> c) {
        Field lines = null;
        try {
            lines = c.getDeclaredField(fieldName);

            int modifiers = lines.getModifiers();
            if (!Modifier.isPrivate(modifiers)) {
                fail(format("Expected the %s field to be private but it is not", fieldName));
            }

            Type fieldBasicType = lines.getType();
            assertEquals(
                    ArrayList.class,
                    fieldBasicType,
                    format(
                            "Expected the %s %s field to be an ArrayList of String, but got %s",
                            className,
                            fieldName,
                            fieldBasicType.getTypeName()
                    )
            );

            ParameterizedType fieldType = (ParameterizedType) lines.getGenericType();
            Class<?> listGenericType = (Class<?>) fieldType.getActualTypeArguments()[0];
            assertEquals(
                    String.class,
                    listGenericType,
                    format(
                            "Expected the %s %s field to be an ArrayList of String, but got %s",
                            className,
                            fieldName,
                            listGenericType.getSimpleName()
                    )
            );
        } catch (NoSuchFieldException e) {
            fail(format("Expected %s to have a private field named %s but it does not", className, fieldName));
        }
        return lines;
    }

    public static Object getInstance(String className, Class<?> c) {
        Object instance = null;
        try {
            instance = c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            fail(format("Call to `new %s()` failed", className));
        }
        return instance;
    }

    public static Class<?> getaClass(String className) {
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail(format("Expected to find a class named `%s` but found nothing", className));
        }
        return c;
    }

    public static String getRandomString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
