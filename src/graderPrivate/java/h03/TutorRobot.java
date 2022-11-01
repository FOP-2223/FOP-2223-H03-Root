package h03;

import fopbot.Direction;
import fopbot.KarelWorld;
import fopbot.Robot;
import fopbot.RobotFamily;

import java.util.ArrayList;
import java.util.List;

class TutorRobot extends Robot {
    public TutorRobot(int x, int y) {
        super(x, y);
    }

    public TutorRobot(int x, int y, RobotFamily family) {
        super(x, y, family);
    }

    List<CallToTutorRobotConstructorIntIntDirectionInt> callsToTutorRobotConstructorIntIntDirectionInt = new ArrayList<>();

    public TutorRobot(int x, int y, Direction direction, int numberOfCoins) {
        super(x, y, direction, numberOfCoins);
        callsToTutorRobotConstructorIntIntDirectionInt.add(
            new CallToTutorRobotConstructorIntIntDirectionInt(x, y, direction, numberOfCoins));
    }

    public TutorRobot(int x, int y, Direction direction, int numberOfCoins, RobotFamily family) {
        super(x, y, direction, numberOfCoins, family);
    }

    public TutorRobot(KarelWorld world, int x, int y) {
        super(world, x, y);
    }

    public TutorRobot(KarelWorld world, int x, int y, RobotFamily family) {
        super(world, x, y, family);
    }

    public TutorRobot(KarelWorld world, int x, int y, Direction direction, int numberOfCoins) {
        super(world, x, y, direction, numberOfCoins);
    }

    public TutorRobot(KarelWorld world, int x, int y, Direction direction, int numberOfCoins, RobotFamily family) {
        super(world, x, y, direction, numberOfCoins, family);
    }
}
