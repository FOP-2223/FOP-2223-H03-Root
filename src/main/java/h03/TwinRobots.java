package h03;

import fopbot.*;

public class TwinRobots {
    private RobotWithOffspring[] robots;

    /**
     * Constructor of TwinRobots. Initializes the twin1 and twin2 fields with two
     * new instances of the Robot class.
     */
    public TwinRobots(int numberOfColumnsOfWorld, int numberOfRowsOfWorld) {
        robots = new RobotWithOffspring[2];
        robots[0] = new RobotWithOffspring(numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.RIGHT, 0);
        robots[1] = new RobotWithOffspring2(numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.UP, 0);
        robots[0].initOffspring(Direction.LEFT, 0);
        robots[1].initOffspring(Direction.LEFT, 0);
    }

    // TODO: JavaDoc
    public RobotWithOffspring getRobotByIndex(int index) {
        return robots[index];
    }

    // TODO: JavaDoc
    public void addToDirectionOfBothOffsprings(int directionToBeAdded) {
        robots[0].addToDirectionOfOffspring(directionToBeAdded);
        robots[1].addToDirectionOfOffspring(directionToBeAdded);
    }
}
