package h03;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h03.ReflectionUtils.AttributeMatcher;
import h03.ReflectionUtils.ClassTester;
import h03.ReflectionUtils.MethodTester;
import h03.ReflectionUtils.ParameterMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h03")
@DisplayName("H1.2")
public class TutorTests_H1_2 {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
        World.setSize(500, 500);
    }

    final String class_name = "RobotWithOffspring";

    @Test
    @DisplayName("1 | Attribut offspring")
    public void t01() {
        robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("offspring", 1, Modifier.PROTECTED,
                Robot.class));
        // TODO: final keyword zulassen? derzeit verboten und Punktabzug, wenn nicht explizit gefordert
    }

    @Test
    @DisplayName("2 | Methode initOffspring")
    // TODO: parametrisierter Test
    // TODO: nochmal kontrollieren
    public void t02() {
        Field offspringField = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("offspring", 0.8, Modifier.PROTECTED,
                Robot.class));

        var methodTester = new MethodTester(robotWithOffspringCT
            .resolve(), "initOffspring", 0.8, Modifier.PUBLIC,
            void.class, new ArrayList<>(List.of(new ParameterMatcher("direction", 0.8, Direction.class),
            new ParameterMatcher("numberOfCoins", 0.8, int.class)))).verify();

        assertDoesNotThrow(() -> offspringField.setAccessible(true));
        Object robotInstance = robotWithOffspringCT.getClassInstance();

        assertDoesNotThrow(() -> ((Robot) robotInstance).setX(264));
        assertDoesNotThrow(() -> ((Robot) robotInstance).setY(123));

        methodTester.invoke(Direction.DOWN, 192);
        Object offspring = assertDoesNotThrow(() -> offspringField.get(robotInstance));
        assertNotNull(offspring, "offspring ist null auch nachdem initOffspring aufgerufen wurde.");
        assertEquals(Robot.class, offspring.getClass(), "initOffspring erstellt ein Objekt vom falschen Typ.");
        assertEquals(264, ((Robot) offspring).getX(), "Das Attribut x des offspring-Objekts wird nicht korrekt " +
            "gesetzt.");
        assertEquals(123, ((Robot) offspring).getY(), "Das Attribut y des offspring-Objekts wird nicht korrekt " +
            "gesetzt.");
        assertEquals(Direction.DOWN, ((Robot) offspring).getDirection(), "Das Attribut direction des " +
            "offspring-Objekts wird nicht korrekt gesetzt.");
        assertEquals(192, ((Robot) offspring).getNumberOfCoins(), "Das Attribut numberOfCoins des offspring-Objekts " +
            "wird nicht korrekt gesetzt.");
    }

    @Test
    @DisplayName("3 | Offspring-Getter")
    public void t03() {
        ClassTester<?> ct = robotWithOffspringCT.resolve();
        Field offspringField = ct.resolveAttribute(
            new AttributeMatcher("offspring", 0.8, Modifier.PROTECTED,
                Robot.class));

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

//
//    @Test
//    public void offspringFieldAndInitOffspringMethodCorrectlyImplemented() {
//        Assertions.assertAll(
//            () -> Utilities.assertFieldExists(RobotWithOffspring.class, "offspring", new Class[]{Robot.class},
//                Scope.PROTECTED),
//            // TODO: Integer abfangen (erlaubte Typen in den Parametern)
//            // TODO: Namen der Parameter überprüfen?
//            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "initOffspring",
//                new Class[]{Direction.class, int.class}, new Class[]{void.class})
//        );
//
//        World.setSize(6, 7);
//        var sut = new RobotWithOffspring(6, 7, Direction.LEFT, 23);
//        var offspringField = Utilities.assertFieldExists(RobotWithOffspring.class, "offspring",
//            new Class[]{Robot.class}, Scope.PRIVATE);
//        try {
//            offspringField.setAccessible(true);
//            var offspring = (Robot) offspringField.get(sut);
//            assertNull(offspring, "offspring ist nach Erstellen eines neuen RobotWithOffspring Objektes nicht mit " +
//                "null initialisiert.");
//
//            var xField = FieldEntity.class.getDeclaredField("x");
//            xField.setAccessible(true);
//            xField.set(sut, 4);
//            var yField = FieldEntity.class.getDeclaredField("y");
//            yField.setAccessible(true);
//            yField.set(sut, 6);
//
//            sut.initOffspring(Direction.DOWN, 78);
//            // TODO: find better way that does not retrieve the field twice
//            final var initializedOffspring = (Robot) offspringField.get(sut);
//
//            Assertions.assertAll(
//                () -> assertEquals(4, initializedOffspring.getX(), "Die X-Position des offspring-Roboters ist nicht
//                " +
//                    "korrekt."),
//                () -> assertEquals(6, initializedOffspring.getY(), "Die Y-Position des offspring-Roboters ist nicht
//                " +
//                    "korrekt."),
//                () -> assertEquals(Direction.DOWN, initializedOffspring.getDirection(), "Die Blickrichtung des " +
//                    "offspring-Roboters ist nicht korrekt."),
//                () -> assertEquals(78, initializedOffspring.getNumberOfCoins(), "Die Anzahl Münzen des " +
//                    "offspring-Roboters ist nicht korrekt.")
//            );
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            throw new AssertionFailedError(String.format("Ausnahme: %s", e.getMessage()));
//        }
//    }
//
//    @Test
//    public void gettersCorrectlyImplemented() {
//        Assertions.assertAll(
//            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getXOfOffspring", null,
//                new Class[]{int.class, Integer.class}),
//            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getYOfOffspring", null,
//                new Class[]{int.class, Integer.class}),
//            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getDirectionOfOffspring", null,
//                new Class[]{Direction.class}),
//            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getNumberOfCoinsOfOffspring", null,
//                new Class[]{int.class, Integer.class})
//        );
//
//        // TODO: check that the getters return the correct results
//    }
//
//    @Test
//    public void offspringIsInitializedCorrectlyImplemented() {
//        Utilities.assertPublicMethodExists(RobotWithOffspring.class, "offspringIsInitialized", null,
//            new Class[]{boolean.class, Boolean.class});
//
//        World.setSize(5, 5);
//        var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
//        assertFalse(sut.offspringIsInitialized(), "offspringIsInitialized gibt nicht false zurück, wenn der
//        offspring" +
//            " noch nicht initialisiert wurde.");
//        // TODO: was, wenn es initOffspring nicht gibt? keine Punkte dann auch für diese Aufgabe?
//        sut.initOffspring(Direction.UP, 0);
//        assertTrue(sut.offspringIsInitialized(), "offspringIsInitialized gibt nicht true zurück, wenn der offspring
//        " +
//            "initialisiert wurde.");
//    }
}
