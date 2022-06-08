package h03;

import fopbot.Direction;
import fopbot.Robot;

public class RobotWithOffspring extends Robot {
  private int numberOfColumnsOfWorld;
  private int numberOfRowsOfWorld;
  private Robot offspring;

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
  public RobotWithOffspring(int numberOfColumnsOfWorld, int numberOfRowsOfWorld, Direction direction,
      int numberOfCoins) {
    super((numberOfColumnsOfWorld % 2 == 0 ? numberOfColumnsOfWorld / 2 : (numberOfColumnsOfWorld + 1) / 2) - 1,
        (numberOfRowsOfWorld % 2 == 0 ? numberOfRowsOfWorld / 2 : (numberOfRowsOfWorld + 1) / 2) - 1, direction,
        numberOfCoins);

    this.numberOfColumnsOfWorld = numberOfColumnsOfWorld;
    this.numberOfRowsOfWorld = numberOfRowsOfWorld;
  }

  /**
   * Initializes the offspring with a new Robot instance
   *
   * @param direction     The initial direction the offspring points at.
   * @param numberOfCoins The initial amount of coins the offspring owns.
   */
  public void initOffspring(Direction direction, int numberOfCoins) {
    this.offspring = new Robot(getX(), getY(), direction, numberOfCoins);
  }

  /**
   * Returns the current x-coordinate of the offspring.
   *
   * @return The current x-coordinate of the offspring.
   */
  public int getXOfOffspring() {
    return offspring.getX();
  }

  /**
   * Returns the current y-coordinate of the offspring.
   *
   * @return The current y-coordinate of the offspring.
   */
  public int getYOfOffspring() {
    return offspring.getY();
  }

  /**
   * Returns the current direction the offspring points at.
   *
   * @return The current direction the offspring points at.
   */
  public Direction getDirectionOfOffspring() {
    return offspring.getDirection();
  }

  /**
   * Returns the current amount of coins the offspring owns.
   *
   * @return The current amount of coins the offspring owns.
   */
  public int getNumberOfCoinsOfOffspring() {
    return offspring.getNumberOfCoins();
  }

  /**
   * Returns a boolean value indicating whether the offspring has been
   * initialized.
   *
   * @return Whether the offspring has been initialized.
   */
  public boolean offspringIsInitialized() {
    return offspring != null;
  }

  /**
   * If the offspring has been initialized, adds the given value to the
   * x-coordinate of the offspring. If the target coordinate is not in the range
   * of columns of the world, the offspring is placed at the border, as close to
   * the target coordinate as possible.
   *
   * @param x The value to be added to the current x-coordinate of the offspring.
   */
  public void addToXOfOffspring(int x) {
    if (offspringIsInitialized()) {
      int n = offspring.getX() + x;

      if (n < (numberOfColumnsOfWorld - 1)) {
        offspring.setX(0);
      } else if ((numberOfColumnsOfWorld - 1) < n) {
        offspring.setX(numberOfColumnsOfWorld - 1);
      } else {
        offspring.setX(n);
      }
    }
  }

  /**
   * If the offspring has been initialized, adds the given value to the
   * y-coordinate of the offspring. If the target coordinate is not in the range
   * of columns of the world, the offspring is placed at the border, as close to
   * the target coordinate as possible.
   *
   * @param y The value to be added to the current y-coordinate of the offspring.
   */
  public void addToYOfOffspring(int y) {
    if (offspringIsInitialized()) {
      int n = offspring.getY() + y;

      if (n < (numberOfRowsOfWorld - 1)) {
        offspring.setY(0);
      } else if ((numberOfRowsOfWorld - 1) < n) {
        offspring.setY(numberOfRowsOfWorld - 1);
      } else {
        offspring.setY(n);
      }
    }
  }

  /**
   * Takes an integer and adds that number of turns to the current direction of
   * the offspring. Uses modular arithmetic to calculate the target direction and
   * then turns the offspring to point in that direction.
   *
   * @param directionToBeAdded The integer value to be added to the given
   *                           direction. Represents the number of right turns of
   *                           the offspring.
   */
  public void addToDirectionOfOffspring(int directionToBeAdded) {
    if (offspringIsInitialized()) {
      Direction targetDirection;
      int sum = getDirectionOfOffspring().ordinal() + directionToBeAdded;

      if (sum >= 0) {
        targetDirection = Direction.values()[sum % 4];
      } else {
        // TODO: check that this is correct
        targetDirection = Direction.values()[(4 - ((-sum) % 4)) % 4];
      }

      while (getDirectionOfOffspring() != targetDirection) {
        offspring.turnLeft();
      }
    }
  }

  /**
   * Adds the given number of coins to the coins the offspring currently owns.
   *
   * @param numberOfCoins The number of coins to be added.
   */
  public void addToNumberOfCoinsOfOffspring(int numberOfCoins) {
    if (offspringIsInitialized()) {
      int n = offspring.getNumberOfCoins() + numberOfCoins;
      // TODO
    }
  }
}
