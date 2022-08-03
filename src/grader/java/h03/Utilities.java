package h03;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class Utilities {
    public static @Nullable Method assertPublicMethodExists(@NotNull Class<?> containingClass, String name, Class<?>[] argumentTypes, Class<?>[] allowedReturnTypes) {
        try {
            final var method = containingClass.getDeclaredMethod(name, argumentTypes);

            Assertions.assertAll(
                () -> {
                    var actualReturnType = method.getReturnType();
                    assertTrue(Arrays.asList(allowedReturnTypes).contains(actualReturnType),
                        MessageFormat.format("Rückgabetyp von {0} ist nicht korrekt. Erwartet wurde einer aus ({1}), der Typ ist aber {2}.", name, String.join(", ", Arrays.stream(allowedReturnTypes).map(Class::getName).toArray(String[]::new)), actualReturnType.getName()));
                },
                () -> assertTrue(Modifier.isPublic(method.getModifiers()), MessageFormat.format("Methode {0} ist nicht public.", name))
            );

            return method;
        } catch (NoSuchMethodException e) {
            fail(MessageFormat.format("Methode {0} mit den Parametern ({1}) wurde in {2} nicht gefunden.", name, String.join(", ", Arrays.stream(argumentTypes).map(Class::getName).toArray(String[]::new)), containingClass.getName()));
        }
        return null;
    }

    public static void assertPublicConstructorExists(@NotNull Class<?> containingClass, Class<?>[] argumentTypes) {
        try {
            final var constructor = containingClass.getDeclaredConstructor(argumentTypes);

            assertTrue(Modifier.isPublic(constructor.getModifiers()), "Konstruktor ist nicht public.");
        } catch (NoSuchMethodException e) {
            fail(MessageFormat.format("Konstruktor mit den Parametern ({1}) wurde in {2} nicht gefunden.", String.join(", ", Arrays.stream(argumentTypes).map(Class::getName).toArray(String[]::new)), containingClass.getName()));
        }
    }

    public static Field assertFieldExists(@NotNull Class<?> containingClass, String name, Class<?>[] allowedTypes, Scope scope) {
        try {
            final var field = containingClass.getDeclaredField(name);

            Assertions.assertAll(
                () -> {
                    var actualType = field.getType();
                    assertTrue(Arrays.asList(allowedTypes).contains(actualType),
                        MessageFormat.format("Typ von {0} ist nicht korrekt. Erwartet wurde einer aus ({1}), der Typ ist aber {2}.", name, String.join(", ", Arrays.stream(allowedTypes).map(Class::getName).toArray(String[]::new)), actualType.getName()));
                },
                () -> {
                    switch (scope) {
                        case PRIVATE ->
                            assertTrue(Modifier.isPrivate(field.getModifiers()), MessageFormat.format("Methode {0} ist nicht private.", name));
                        case PROTECTED ->
                            assertTrue(Modifier.isProtected(field.getModifiers()), MessageFormat.format("Methode {0} ist nicht protected.", name));
                        case PUBLIC ->
                            assertTrue(Modifier.isPublic(field.getModifiers()), MessageFormat.format("Methode {0} ist nicht public.", name));
                    }
                }
            );

            return field;
        } catch (NoSuchFieldException e) {
            fail(MessageFormat.format("Attribut {0} wurde in {1} nicht gefunden.", name, containingClass.getName()));
            return null;
        }
    }

    static <TValue, TClass> TValue getValueOfField(@NotNull Class owner, String name, @NotNull TClass object) throws IllegalAccessException, NoSuchFieldException {
        Field field = owner.getDeclaredField(name);
        field.setAccessible(true);
        return (TValue) field.get(object);
    }
}
