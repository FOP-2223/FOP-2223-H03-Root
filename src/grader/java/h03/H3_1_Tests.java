package h03;

import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestForSubmission("h03")
public class H3_1_Tests {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
    }

    @Test
    public void robotsArrayCorrectlyImplemented() {
        Utilities.assertFieldExists(TwinRobots.class, "robots", new Class[]{RobotWithOffspring[].class}, Scope.PRIVATE);
    }

    @Test
    public void constructorCorrectlyImplemented() throws IllegalAccessException {
        Utilities.assertPublicConstructorExists(TwinRobots.class, new Class[]{int.class, int.class});
        var sut = new TwinRobots(23, 20);

        try {
            var robots = (RobotWithOffspring[]) Utilities.getValueOfField(TwinRobots.class, "robots", new Class[]{RobotWithOffspring[].class});
            var robotWithOffspring = robots[0];
            assertEquals(RobotWithOffspring.class, robotWithOffspring.getClass(), "Das erste Element im robots Array ist nicht vom Typ RobotWithOffspring.");
            assertEquals(robotWithOffspring.);

            assertEquals(RobotWithOffspring2.class, robots[1].getClass());
        } catch (NoSuchFieldException e) {
            fail("Der robots Array wurde nicht gefunden.");
        }


        // TODO: access private field and check it
    }

    @Test
    public void getRobotByIndexCorrectlyImplemented() throws IllegalAccessException {
        var robotsField = Utilities.assertFieldExists(TwinRobots.class, "robots", new Class[]{RobotWithOffspring[].class}, Scope.PRIVATE);
        Utilities.assertPublicMethodExists(TwinRobots.class, "getRobotByIndex", new Class[]{int.class}, new Class[]{RobotWithOffspring.class});
        robotsField.setAccessible(true);

        var sut = new TwinRobots(1, 1);
        var expectedRobot1 = new RobotWithOffspring(1, 2, Direction.UP, 12);
        var expectedRobot2 = new RobotWithOffspring2(3, 4, Direction.DOWN, 56);

        robotsField.set(sut, new RobotWithOffspring[]{expectedRobot1, expectedRobot2});
        var actualRobot1 = sut.getRobotByIndex(0);
        var actualRobot2 = sut.getRobotByIndex(1);
        assertEquals(expectedRobot1, actualRobot1);
        assertEquals(expectedRobot2, actualRobot2);
        //TODO: message
    }

    @Test
    public void addToDirectionOfBothOffspringsCorrectlyImplemented() throws IllegalAccessException {
        var robotsField = Utilities.assertFieldExists(TwinRobots.class, "robots", new Class[]{RobotWithOffspring[].class}, Scope.PRIVATE);
        Utilities.assertPublicMethodExists(TwinRobots.class, "addToDirectionOfBothOffsprings", new Class[]{int.class}, new Class[]{void.class});
        robotsField.setAccessible(true);

        var sut = new TwinRobots(1, 1);
        var robotArray = new FakeRobotWithOffspring[]{
            new FakeRobotWithOffspring(1, 2, Direction.UP, 12),
            new FakeRobotWithOffspring(3, 4, Direction.DOWN, 56)
        };

        robotsField.set(sut, robotArray);
        sut.addToDirectionOfBothOffsprings(15);
        sut.addToDirectionOfBothOffsprings(-12);

        for (var robot : robotArray) {
            assertEquals(2, robot.getDirectionsAdded().size());
            assertEquals(15, robot.getDirectionsAdded().get(0));
            assertEquals(-12, robot.getDirectionsAdded().get(1));
        }

        // TODO: messages
    }
}
