package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestForSubmission
@DisplayName("H1.2")
public class TutorTests_H1_2 {
    @BeforeAll
    public static void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);
    }

    // DONE
    @Test
    @DisplayName("Attribut \"offspring\" wurde korrekt deklariert.")
    public void offspringDeclaredCorrectly() {
        var attribute = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("offspring", MIN_SIM, Modifier.PROTECTED, Robot.class));

        assertFalse(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"offspring\" ist ein Array, sollte aber kein Array sein.");
    }

    // DONE
    @Test
    @DisplayName("Methode \"initOffspring\" wurde korrekt deklariert.")
    public void initOffspringDeclaredCorrectly() {
        new MethodTester(robotWithOffspringCT.resolve(), "initOffspring", MIN_SIM, Modifier.PUBLIC,
            void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("direction", MIN_SIM_PARAM, Direction.class),
                new ParameterMatcher("numberOfCoins", MIN_SIM_PARAM, int.class)
            ))).verify();
    }

    // DONE
    @ParameterizedTest
    @CsvFileSource(resources = "/TutorTests_H1_2-initOffspringImplementedCorrectly.csv", numLinesToSkip = 1)
    @DisplayName("Methode \"initOffspring\" wurde korrekt implementiert.")
    public void initOffspringImplementedCorrectly(int x, int y, Direction direction, int numberOfCoins) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException {
        final Field offspringField = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("offspring", MIN_SIM, Robot.class));
        assertFalse(offspringField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                offspringField.getName()));

        var methodTester = new MethodTester(robotWithOffspringCT
            .resolve(), "initOffspring", MIN_SIM, Modifier.PUBLIC,
            void.class, new ArrayList<>(List.of(
            new ParameterMatcher("direction", MIN_SIM_PARAM, Direction.class),
            new ParameterMatcher("numberOfCoins", MIN_SIM_PARAM, int.class)))).verify();

        Robot robotInstance = (Robot) robotWithOffspringCT.resolveRealInstance().getClassInstance();

        robotInstance.setX(x);
        robotInstance.setY(y);

        methodTester.invoke(direction, numberOfCoins);

        Object offspring = assertDoesNotThrow(() -> offspringField.get(robotInstance));
        assertNotNull(offspring, "offspring ist null auch nachdem initOffspring aufgerufen wurde.");
        assertEquals(Robot.class, offspring.getClass(),
            "offspring ist nach Aufruf von initOffspring nicht vom Typ Robot.");
        assertEquals(x, ((Robot) offspring).getX(),
            "Das Attribut x des offspring-Objekts wird nicht korrekt gesetzt.");
        assertEquals(y, ((Robot) offspring).getY(),
            "Das Attribut y des offspring-Objekts wird nicht korrekt gesetzt.");
        assertEquals(direction, ((Robot) offspring).getDirection(),
            "Das Attribut direction des offspring-Objekts wird nicht korrekt gesetzt.");
        assertEquals(numberOfCoins, ((Robot) offspring).getNumberOfCoins(),
            "Das Attribut numberOfCoins des offspring-Objekts wird nicht korrekt gesetzt.");
    }

    // DONE
    @ParameterizedTest
    @CsvFileSource(resources = "/TutorTests_H1_2-allOffspringGetterMethodsCorrectlyDeclaredAndImplemented.csv", numLinesToSkip
        = 1)
    @DisplayName("Die get-Methoden für den offspring wurden korrekt deklariert und implementiert.")
    public void allOffspringGetterMethodsCorrectlyDeclaredAndImplemented(int x, int y, Direction direction, int numberOfCoins) throws IllegalAccessException {
        ClassTester<?> ct = robotWithOffspringCT.resolve();
        Field offspringField = ct.resolveAttribute(
            new AttributeMatcher("offspring", MIN_SIM, Robot.class));
        assertFalse(offspringField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                offspringField.getName()));

        Object robotInstance = robotWithOffspringCT.getClassInstance();

        var offspring = new Robot(x, y, direction, numberOfCoins);
        offspringField.set(robotInstance, offspring);

        testGetterMethod("getXOfOffspring", int.class, ct, x);
        testGetterMethod("getYOfOffspring", int.class, ct, y);
        testGetterMethod("getDirectionOfOffspring", Direction.class, ct, direction);
        testGetterMethod("getNumberOfCoinsOfOffspring", int.class, ct, numberOfCoins);
    }

    private void testGetterMethod(String getterName, Class<?> returnType, ClassTester<?> ct, Object expectedValue) {
        var methodTester = new MethodTester(ct, getterName, MIN_SIM, Modifier.PUBLIC,
            returnType, new ArrayList<>()).verify();
        var returnValue = methodTester.invoke();

        assertEquals(expectedValue, returnValue,
            String.format("Die Methode \"%s\" liefert falsche Werte zurück.", getterName));
    }

    // DONE
    @Test
    @DisplayName("Methode \"offspringIsInitialized\" wurde korrekt deklariert und implementiert.")
    public void offspringIsInitializedDeclaredAndImplementedCorrectly() throws IllegalAccessException {
        Field offspringField = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("offspring", MIN_SIM, Robot.class));
        assertFalse(offspringField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                offspringField.getName()));

        var methodTester = new MethodTester(
            robotWithOffspringCT.assureClassResolved(),
            "offspringIsInitialized",
            MIN_SIM,
            Modifier.PUBLIC,
            boolean.class,
            new ArrayList<>()).verify();

        var returnValueBeforeCall = methodTester.invoke();
        assertEquals(false, returnValueBeforeCall,
            "\"offspringIsInitialized\" liefert true zurück, bevor der offspring initialisiert wurde.");

        Object robotInstance = robotWithOffspringCT.getClassInstance();

        // Both the initOffspring method is called (if found) and the offspring is set manually via reflection. Here's the reasoning:
        // We want to make sure that we give points for this method even if initOffspring is implemented wrongly, so we set
        // the offspring manually.
        // We still call initOffspring if found, in case the student does not check for null, but
        // sets a boolean variable indicating whether the offspring has been initialized (since this would
        // also be a valid solution).
        try {
            var initOffspringMethodTester = new MethodTester(robotWithOffspringCT.assureClassResolved(),
                "initOffspring", MIN_SIM, Modifier.PUBLIC,
                void.class,
                new ArrayList<>(List.of(
                    new ParameterMatcher("direction", MIN_SIM_PARAM, Direction.class),
                    new ParameterMatcher("numberOfCoins", MIN_SIM_PARAM, int.class)
                ))).resolveMethod();
            initOffspringMethodTester.invoke(robotInstance, Direction.UP, 0);
        } catch (AssertionFailedError | InvocationTargetException ex) {
            // Nothing to do. The method should be called if found, but we ignore it if not.
        }

        var offspring = new Robot(0, 0);
        offspringField.set(robotInstance, offspring);

        var returnValueAfterCall = methodTester.invoke();
        assertEquals(true, returnValueAfterCall,
            "\"offspringIsInitialized\" liefert false zurück, nachdem der offspring initialisiert wurde.");
    }
}
