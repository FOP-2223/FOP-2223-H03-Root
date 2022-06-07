package h03;

import fopbot.Direction;
import fopbot.World;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        robotTests();
    }

    private static void robotTests() {
        World.setSize(3, 7);
        World.setVisible(true);

        // RobotWithOffspring foo = new RobotWithOffspring(3,7, Direction.LEFT, 344);
        var two = new RobotWithOffspring2(3, 7, Direction.LEFT, 34);
        two.initOffspring(Direction.DOWN, 44);

        int foo = 3 * 1_000_000_000;
        System.out.println(foo);
    }
}
