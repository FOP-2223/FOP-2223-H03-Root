package h03;

import fopbot.Direction;

public class TwinRobots {
    private RobotWithOffspring[] robots;

    /**
     * Constructor of TwinRobots. Instantiates the two robots and initializes their
     * offsprings. The robots are saved in the internal array robots.
     */
    public TwinRobots(int numberOfColumnsOfWorld, int numberOfRowsOfWorld) {
        robots = new RobotWithOffspring[2];
        robots[0] = new RobotWithOffspring(numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.RIGHT, 0);
        robots[1] = new RobotWithOffspring2(numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.UP, 0);
        robots[0].initOffspring(Direction.LEFT, 0);
        robots[1].initOffspring(Direction.LEFT, 0);
    }

    /**
     * Returns the twin robot at the given index in the internal array.
     *
     * @param index The index at which to retrieve the robot from the array.
     *
     * @return The twin robot at the given index.
     */
    public RobotWithOffspring getRobotByIndex(int index) {
        return robots[index];
    }

    /**
     * Adds the integer value to the current direction of both twin robots.
     *
     * @param directionToBeAdded The integer value representing how many times to
     *                           turn the robots right.
     */
    public void addToDirectionOfBothOffsprings(int directionToBeAdded) {
        robots[0].addToDirectionOfOffspring(directionToBeAdded);
        robots[1].addToDirectionOfOffspring(directionToBeAdded);
    }
}
