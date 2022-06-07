package h03;

import fopbot.Direction;

public class RobotWithOffspring2 extends RobotWithOffspring {
  private int directionAccu;

  /**
   * Constructor of RobotWithOffspring2. Calls the super constructor.
   *
   * @param numberOfColumnsOfWorld Number of columns in the world the robot is supposed to be placed in.
   * @param numberOfRowsOfWorld    Number of row in the world the robot is supposed to be placed in.
   * @param direction              The initial direction the robot points at.
   * @param numberOfCoins          The initial amount of coins the robot owns.
   */
  public RobotWithOffspring2(int numberOfColumnsOfWorld, int numberOfRowsOfWorld, Direction direction, int numberOfCoins) {
    super(numberOfColumnsOfWorld, numberOfRowsOfWorld, direction, numberOfCoins);
  }

  /**
   * Initializes the offspring and initializes the directionAccu with the value representing the given direction.
   *
   * @param direction     The initial direction the offspring points at.
   * @param numberOfCoins The initial amount of coins the offspring owns.
   */
  @Override
  public void initOffspring(Direction direction, int numberOfCoins) {
    super.initOffspring(direction, numberOfCoins);

    if (direction == Direction.UP) {
      directionAccu = 0;
    } else if (direction == Direction.RIGHT) {
      directionAccu = 1;
    } else if (direction == Direction.DOWN) {
      directionAccu = 2;
    } else {
      directionAccu = 3;
    }
  }

  /**
   * Calculates the current direction from the directionAccu value.
   *
   * @return The current direction representing the directionAccu value.
   */
  private Direction getDirectionFromAccu() {
    Direction result;
    int n = directionAccu % 4;

    if (n == 0) {
      result = Direction.UP;
    } else if (n == 1) {
      result = Direction.RIGHT;
    } else if (n == 2) {
      result = Direction.DOWN;
    } else {
      result = Direction.LEFT;
    }

    return result;
  }

  /**
   * Returns the current direction the offspring is pointing at.
   *
   * @return The current direction the offspring is pointing at.
   */
  @Override
  public Direction getDirectionOfOffspring() {
    return getDirectionFromAccu();
  }

  /**
   * Adds the given integer value to the current direction of the offspring. Updates the directionAccu value and makes the offspring point in the respective direction.
   *
   * @param directionToBeAdded The integer value to be added to the given direction. Represents the number of right turns of the offspring.
   */
  @Override
  public void addToDirectionOfOffspring(int directionToBeAdded) {
    directionAccu += directionToBeAdded;
    Direction directionFromAccu = getDirectionFromAccu();

    while (getDirection() != directionFromAccu) {
      turnLeft();
    }
  }
}
