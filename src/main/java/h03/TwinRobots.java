package h03;

import fopbot.Direction;
import fopbot.Robot;

public class TwinRobots {
  private Robot twin1;
  private Robot twin2;
  private boolean firstTwinIsActive;

  /**
   * Constructor of TwinRobots. Initializes the twin1 and twin2 fields with two new instances of the Robot class.
   */
  public TwinRobots() {
    twin1 = new Robot(1, 0, Direction.RIGHT, 0);
    twin2 = new Robot(0, 1, Direction.UP, 0);
  }

  /**
   * Sets the second twin to active if the first was active before and vice versa. Deactivates the other twin.
   */
  public void toggleActiveRobot() {
    firstTwinIsActive = !firstTwinIsActive;
  }

  /**
   * Returns the currently activated twin.
   *
   * @return The currently activated twin robot.
   */
  public Robot getActiveRobot() {
    return firstTwinIsActive ? twin1 : twin2;
  }
}
