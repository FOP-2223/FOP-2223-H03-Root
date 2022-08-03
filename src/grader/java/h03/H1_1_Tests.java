package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import h03.ArgumentsProviders.WorldArgumentsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h03")
public class H1_1_Tests {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
    }

    @ParameterizedTest
    @ArgumentsSource(WorldArgumentsProvider.class)
    public void constructorSetsCorrectPosition(int worldWidth, int worldHeight) {
        World.setSize(worldWidth, worldHeight);
        int actualX;
        int actualY;

        try {
            var sut = new RobotWithOffspring(worldWidth, worldHeight, Direction.DOWN, 0);
            actualX = sut.getX();
            actualY = sut.getY();
        } catch (RuntimeException e) {
            throw new AssertionFailedError(String.format("Ausnahme: %s", e.getMessage()));
        }

        var expectedX = worldWidth / 2;
        var expectedY = worldHeight / 2;
        assertEquals(expectedX, actualX);
        assertEquals(expectedY, actualY);
    }

    @ParameterizedTest
    @ArgumentsSource(WorldArgumentsProvider.class)
    public void constructorSetsAttributes(int worldWidth, int worldHeight) throws IllegalAccessException {
        World.setSize(worldWidth, worldHeight);
        int actualNumberOfColumnsOfWorld;
        int actualNumberOfRowsOfWorld;

        try {
            var sut = new RobotWithOffspring(worldWidth, worldHeight, Direction.DOWN, 0);
            Field numberOfColumnsOfWorldField = sut.getClass().getDeclaredField("numberOfColumnsOfWorld");
            numberOfColumnsOfWorldField.setAccessible(true);
            actualNumberOfColumnsOfWorld = (int) numberOfColumnsOfWorldField.get(sut);
            Field numberOfRowsOfWorldField = sut.getClass().getDeclaredField("numberOfRowsOfWorld");
            numberOfRowsOfWorldField.setAccessible(true);
            actualNumberOfRowsOfWorld = (int) numberOfRowsOfWorldField.get(sut);
        } catch (RuntimeException | NoSuchFieldException e) {
            throw new AssertionFailedError(String.format("Ausnahme: %s", e.getMessage()));
        }

        assertEquals(worldWidth, actualNumberOfColumnsOfWorld);
        assertEquals(worldHeight, actualNumberOfRowsOfWorld);
    }

    @Test
    public void classInheritsFromRobotAndHasCorrectAttributes() {
        assertTrue(Robot.class.isAssignableFrom(RobotWithOffspring.class),
            String.format("Die Klasse %s erbt nicht von %s.", RobotWithOffspring.class.getTypeName(), Robot.class.getTypeName()));

        assertTrue(Arrays.stream(RobotWithOffspring.class.getDeclaredFields())
                .anyMatch(f ->
                    f.getName().equals("numberOfColumnsOfWorld")
                        && (f.getType().isAssignableFrom(int.class)
                        || f.getType().isAssignableFrom(Integer.class))
                        && Modifier.isPrivate(f.getModifiers())),
            "Das Attribut numberOfColumnsOfWorld wurde nicht korrekt implementiert."
        );

        assertTrue(Arrays.stream(RobotWithOffspring.class.getDeclaredFields())
                .anyMatch(f ->
                    f.getName().equals("numberOfRowsOfWorld")
                        && (f.getType().isAssignableFrom(int.class)
                        || f.getType().isAssignableFrom(Integer.class))
                        && Modifier.isPrivate(f.getModifiers())),
            "Das Attribut numberOfRowsOfWorld wurde nicht korrekt implementiert.");
    }
}
