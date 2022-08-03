package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestForSubmission("h03")
public class H1_3_Tests {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
    }

    @Test
    public void addToXOfOffspringCorrectlyImplemented() throws IllegalAccessException {
        World.setSize(5, 5);

        var sut = new RobotWithOffspring(5, 5, Direction.LEFT, 67);

        // Test that no NullPointerException occurs when offspring is not initialized.
        try {
            sut.addToXOfOffspring(12);
        } catch (NullPointerException e) {
            fail("Die Methode addToXOfOffspring wirft eine NullPointerException, wenn der offspring nicht initialisiert ist.");
        }

        try {
            var offspringField = RobotWithOffspring.class.getDeclaredField("offspring");
            offspringField.setAccessible(true);

            var offspring = initializeNewOffspring(offspringField, sut, 1, 2, Direction.UP, 35);
            sut.addToXOfOffspring(-2);
            assertEquals(0, offspring.getX());

            offspring = initializeNewOffspring(offspringField, sut, 2, 2, Direction.UP, 35);
            sut.addToXOfOffspring(4);
            assertEquals(4, offspring.getX());

            offspring = initializeNewOffspring(offspringField, sut, 1, 2, Direction.UP, 35);
            sut.addToXOfOffspring(2);
            assertEquals(3, offspring.getX());
        } catch (NoSuchFieldException e) {
            fail("Das Attribut offspring wurde in der Klasse RobotWithOffspring nicht gefunden.");
        }
    }

    @Test
    public void addToYOfOffspringCorrectlyImplemented() throws IllegalAccessException {
        World.setSize(5, 5);

        var sut = new RobotWithOffspring(5, 5, Direction.LEFT, 67);

        // Test that no NullPointerException occurs when offspring is not initialized.
        try {
            sut.addToYOfOffspring(12);
        } catch (NullPointerException e) {
            fail("Die Methode addToYOfOffspring wirft eine NullPointerException, wenn der offspring nicht initialisiert ist.");
        }

        try {
            var offspringField = RobotWithOffspring.class.getDeclaredField("offspring");
            offspringField.setAccessible(true);

            var offspring = initializeNewOffspring(offspringField, sut, 2, 1, Direction.UP, 35);
            sut.addToYOfOffspring(-2);
            assertEquals(0, offspring.getY());

            offspring = initializeNewOffspring(offspringField, sut, 2, 2, Direction.UP, 35);
            sut.addToYOfOffspring(4);
            assertEquals(4, offspring.getY());

            offspring = initializeNewOffspring(offspringField, sut, 2, 1, Direction.UP, 35);
            sut.addToYOfOffspring(2);
            assertEquals(3, offspring.getY());
        } catch (NoSuchFieldException e) {
            fail("Das Attribut offspring wurde in der Klasse RobotWithOffspring nicht gefunden.");
        }
    }

    @ParameterizedTest
    @CsvSource({"0,1", "1,2", "2,3", "3,0", "4,1", "-1,0", "-2,3", "-3,2", "-4,1", "-5,0"})
    // TODO: performance optimization: check for NullPointerException should not occur for every run
    public void addToDirectionOfOffspringCorrectlyImplemented(String addedDirectionString, String expectedDirectionString) throws IllegalAccessException {
        World.setSize(5, 5);

        var sut = new RobotWithOffspring(5, 5, Direction.LEFT, 67);

        // Test that no NullPointerException occurs when offspring is not initialized.
        try {
            sut.addToDirectionOfOffspring(0);
        } catch (NullPointerException e) {
            fail("Die Methode addToYOfOffspring wirft eine NullPointerException, wenn der offspring nicht initialisiert ist.");
        }

        try {
            var offspringField = RobotWithOffspring.class.getDeclaredField("offspring");
            offspringField.setAccessible(true);

            var offspring = initializeNewOffspring(offspringField, sut, 2, 1, Direction.RIGHT, 35);

            var added = Integer.parseInt(addedDirectionString);
            sut.addToDirectionOfOffspring(added);
            var actualDirection = offspring.getDirection();

            var expected = Integer.parseInt(expectedDirectionString);
            var expectedDirection = Direction.values()[expected];

            assertEquals(expectedDirection, actualDirection);
        } catch (NoSuchFieldException e) {
            fail("Das Attribut offspring wurde in der Klasse RobotWithOffspring nicht gefunden.");
        }
    }

    @Test
    public void addToNumberOfCoinsOfOffspringCorrectlyImplemented() {
        throw new UnsupportedOperationException("Test not implemented");
    }

    private @NotNull Robot initializeNewOffspring(@NotNull Field offspringField, RobotWithOffspring parent, int x, int y, Direction direction, int numberOfCoins) throws IllegalAccessException {
        var offspring = new Robot(x, y, direction, numberOfCoins);
        offspringField.set(parent, offspring);
        return offspring;
    }
}
