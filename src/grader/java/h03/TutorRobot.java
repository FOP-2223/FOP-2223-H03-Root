package h03;

import fopbot.Direction;
import fopbot.KarelWorld;
import fopbot.Robot;
import fopbot.RobotFamily;
import org.apache.logging.log4j.Logger;
import org.sourcegrade.jagr.launcher.env.Jagr;

public class TutorRobot extends Robot {
    public TutorRobot(int x, int y) {
        super(x, y);
    }

    public TutorRobot(int x, int y, RobotFamily family) {
        super(x, y, family);
    }

    public TutorRobot(int x, int y, Direction direction, int numberOfCoins) {
        super(x, y, direction, numberOfCoins);
        Jagr.Default.getInjector().getInstance(Logger.class).warn("sdassdasdasdasdads");
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
