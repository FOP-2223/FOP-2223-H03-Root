package h03;

import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTests {
    @Test
    void testRobotWithOffspringTwins() {
        World.setSize(10, 10);
        World.setDelay(0);

        var robot = new RobotWithOffspring(10, 10, Direction.RIGHT, 42);
        var robot2 = new RobotWithOffspring2(10, 10, Direction.RIGHT, 42);

        robot.initOffspring(Direction.DOWN, 12);
        robot2.initOffspring(Direction.DOWN, 12);
        assertEquals(robot.getDirectionOfOffspring(), robot2.getDirectionOfOffspring());

        robot.addToDirectionOfOffspring(1);
        robot2.addToDirectionOfOffspring(1);
        assertEquals(robot.getDirectionOfOffspring(), robot2.getDirectionOfOffspring());

        robot.addToDirectionOfOffspring(22);
        robot2.addToDirectionOfOffspring(22);
        assertEquals(robot.getDirectionOfOffspring(), robot2.getDirectionOfOffspring());
    }
}
