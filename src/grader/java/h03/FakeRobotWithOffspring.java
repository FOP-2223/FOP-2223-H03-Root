package h03;

import fopbot.Direction;

import java.util.ArrayList;
import java.util.List;

public class FakeRobotWithOffspring extends RobotWithOffspring {
    private List<Integer> directionsAdded = new ArrayList<Integer>();

    /**
     * Constructor of RobotWithOffspring. Places the robot in the center of the
     * world and saves the given numberOfColumnsOfWorld and numberOfRowsOfWorld.
     *
     * @param numberOfColumnsOfWorld Number of columns in the world the robot is
     *                               supposed to be placed in.
     * @param numberOfRowsOfWorld    Number of row in the world the robot is
     *                               supposed to be placed in.
     * @param direction              The initial direction the robot points at.
     * @param numberOfCoins          The initial amount of coins the robot owns.
     */
    public FakeRobotWithOffspring(int numberOfColumnsOfWorld, int numberOfRowsOfWorld, Direction direction, int numberOfCoins) {
        super(numberOfColumnsOfWorld, numberOfRowsOfWorld, direction, numberOfCoins);
    }

    public List<Integer> getDirectionsAdded() {
        return directionsAdded;
    }

    @Override
    public void addToDirectionOfOffspring(int directionToBeAdded) {
        directionsAdded.add(directionToBeAdded);
    }
}
