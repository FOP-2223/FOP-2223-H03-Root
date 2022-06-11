package h03;

import fopbot.*;

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
        SandboxTests();
    }

    private static void SandboxTests() {
        World.setSize(5, 7);
        World.setVisible(true);

        RobotWithOffspring robot = new RobotWithOffspring(5, 7, Direction.LEFT, 12);
    }
}
