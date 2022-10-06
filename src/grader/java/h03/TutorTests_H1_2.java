package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestForSubmission
@DisplayName("H1.2")
public class TutorTests_H1_2 {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);
    }

    @Test
    @DisplayName("1 | Attribut offspring")
    public void t01() {
        var attribute = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("offspring", 1, Modifier.PROTECTED, Robot.class));

        assertFalse(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"offspring\" ist ein Array, sollte aber kein Array sein.");
    }

    @Test
    @DisplayName("2 | Methode initOffspring")
    // TODO: parametrisierter Test
    public void t02() {
        final Field offspringField = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("offspring", 0.8, Robot.class));
        assertFalse(offspringField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                offspringField.getName()));

        var methodTester = new MethodTester(robotWithOffspringCT
            .resolve(), "initOffspring", 0.8, Modifier.PUBLIC,
            void.class, new ArrayList<>(List.of(
            new ParameterMatcher("direction", 0.8, Direction.class),
            new ParameterMatcher("numberOfCoins", 0.8, int.class)))).verify();

        Object robotInstance = robotWithOffspringCT.getClassInstance();

        ((Robot) robotInstance).setX(264);
        ((Robot) robotInstance).setY(123);

        methodTester.invoke(Direction.DOWN, 192);
        Object offspring = assertDoesNotThrow(() -> offspringField.get(robotInstance));
        assertNotNull(offspring, "offspring ist null auch nachdem initOffspring aufgerufen wurde.");
        assertEquals(Robot.class, offspring.getClass(), "offspring ist nach Aufruf von initOffspring nicht vom Typ "
            + "Robot.");
        assertEquals(264, ((Robot) offspring).getX(),
            "Das Attribut x des offspring-Objekts wird nicht korrekt gesetzt.");
        assertEquals(123, ((Robot) offspring).getY(),
            "Das Attribut y des offspring-Objekts wird nicht korrekt gesetzt.");
        assertEquals(Direction.DOWN, ((Robot) offspring).getDirection(),
            "Das Attribut direction des offspring-Objekts wird nicht korrekt gesetzt.");
        assertEquals(192, ((Robot) offspring).getNumberOfCoins(),
            "Das Attribut numberOfCoins des offspring-Objekts wird nicht korrekt gesetzt.");
    }

    @Test
    @DisplayName("3 | Offspring-Getter")
    // TODO: Parametrisierter Test
    public void t03() {
        ClassTester<?> ct = robotWithOffspringCT.resolve();
        Field offspringField = ct.resolveAttribute(
            new AttributeMatcher("offspring", 0.8, Robot.class));
        assertFalse(offspringField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                offspringField.getName()));

        Object robotInstance = robotWithOffspringCT.getClassInstance();

        var offspring = new Robot(129, 472, Direction.RIGHT, 463);
        assertDoesNotThrow(() -> offspringField.set(robotInstance, offspring));

        testGetterMethod("getXOfOffspring", int.class, ct, 129);
        testGetterMethod("getYOfOffspring", int.class, ct, 472);
        testGetterMethod("getDirectionOfOffspring", Direction.class, ct, Direction.RIGHT);
        testGetterMethod("getNumberOfCoinsOfOffspring", int.class, ct, 463);
    }

    private void testGetterMethod(String getterName, Class<?> returnType, ClassTester<?> ct, Object expectedValue) {
        var methodTester = new MethodTester(
            ct,
            getterName,
            0.8,
            Modifier.PUBLIC,
            returnType,
            new ArrayList<>()).verify();
        var returnValue = methodTester.invoke();

        assertEquals(expectedValue, returnValue, String.format("Falsche Rückgabe der Methode %s.", getterName));
    }

    @Test
    @DisplayName("4 | Methode offspringIsInitialized")
    public void t04() {
        ClassTester<?> ct = robotWithOffspringCT.resolve();
        Field offspringField = ct.resolveAttribute(
            new AttributeMatcher("offspring", 0.8, Robot.class));
        assertFalse(offspringField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                offspringField.getName()));

        var methodTester = new MethodTester(
            ct,
            "offspringIsInitialized",
            0.8,
            Modifier.PUBLIC,
            boolean.class,
            new ArrayList<>()).verify();

        var returnValueBeforeCall = methodTester.invoke();
        assertEquals(false, returnValueBeforeCall,
            "offspringIsInitialized liefert true zurück, bevor der offspring initialisiert wurde.");

        Object robotInstance = robotWithOffspringCT.getClassInstance();
        var offspring = new Robot(0, 0);
        assertDoesNotThrow(() -> offspringField.set(robotInstance, offspring));

        var returnValueAfterCall = methodTester.invoke();
        assertEquals(true, returnValueAfterCall,
            "offspringIsInitialized liefert false zurück, nachdem der offspring initialisiert wurde.");
    }
}
