package h03;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h03")
public class H1_2_Tests {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
    }

    @Test
    public void offspringFieldAndInitOffspringMethodCorrectlyImplemented() {
        Assertions.assertAll(
            () -> Utilities.assertFieldExists(RobotWithOffspring.class, "offspring", new Class[]{Robot.class}, Scope.PROTECTED),
            // TODO: Integer abfangen (erlaubte Typen in den Parametern)
            // TODO: Namen der Parameter überprüfen?
            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "initOffspring", new Class[]{Direction.class, int.class}, new Class[]{void.class})
        );

        World.setSize(6, 7);
        var sut = new RobotWithOffspring(6, 7, Direction.LEFT, 23);
        var offspringField = Utilities.assertFieldExists(RobotWithOffspring.class, "offspring", new Class[]{Robot.class}, Scope.PRIVATE);
        try {
            offspringField.setAccessible(true);
            var offspring = (Robot) offspringField.get(sut);
            assertNull(offspring, "offspring ist nach Erstellen eines neuen RobotWithOffspring Objektes nicht mit null initialisiert.");

            var xField = FieldEntity.class.getDeclaredField("x");
            xField.setAccessible(true);
            xField.set(sut, 4);
            var yField = FieldEntity.class.getDeclaredField("y");
            yField.setAccessible(true);
            yField.set(sut, 6);

            sut.initOffspring(Direction.DOWN, 78);
            // TODO: find better way that does not retrieve the field twice
            final var initializedOffspring = (Robot) offspringField.get(sut);

            Assertions.assertAll(
                () -> assertEquals(4, initializedOffspring.getX(), "Die X-Position des offspring-Roboters ist nicht korrekt."),
                () -> assertEquals(6, initializedOffspring.getY(), "Die Y-Position des offspring-Roboters ist nicht korrekt."),
                () -> assertEquals(Direction.DOWN, initializedOffspring.getDirection(), "Die Blickrichtung des offspring-Roboters ist nicht korrekt."),
                () -> assertEquals(78, initializedOffspring.getNumberOfCoins(), "Die Anzahl Münzen des offspring-Roboters ist nicht korrekt.")
            );
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new AssertionFailedError(String.format("Ausnahme: %s", e.getMessage()));
        }
    }

    @Test
    public void gettersCorrectlyImplemented() {
        Assertions.assertAll(
            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getXOfOffspring", null, new Class[]{int.class, Integer.class}),
            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getYOfOffspring", null, new Class[]{int.class, Integer.class}),
            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getDirectionOfOffspring", null, new Class[]{Direction.class}),
            () -> Utilities.assertPublicMethodExists(RobotWithOffspring.class, "getNumberOfCoinsOfOffspring", null, new Class[]{int.class, Integer.class})
        );

        // TODO: check that the getters return the correct results
    }

    @Test
    public void offspringIsInitializedCorrectlyImplemented() {
        Utilities.assertPublicMethodExists(RobotWithOffspring.class, "offspringIsInitialized", null, new Class[]{boolean.class, Boolean.class});

        World.setSize(5, 5);
        var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
        assertFalse(sut.offspringIsInitialized(), "offspringIsInitialized gibt nicht false zurück, wenn der offspring noch nicht initialisiert wurde.");
        // TODO: was, wenn es initOffspring nicht gibt? keine Punkte dann auch für diese Aufgabe?
        sut.initOffspring(Direction.UP, 0);
        assertTrue(sut.offspringIsInitialized(), "offspringIsInitialized gibt nicht true zurück, wenn der offspring initialisiert wurde.");
    }
}
