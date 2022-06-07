package h03;

import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotWithOffspringTest {
  @Test
  void constructor_OddSizeGiven_PlacedAtCenterAndValuesSaved() {
    World.setSize(9, 19);

    var sut = new RobotWithOffspring(9, 19, Direction.LEFT, 42);

    assertEquals(4, sut.getX());
    assertEquals(9, sut.getY());
    assertEquals(Direction.LEFT, sut.getDirection());
    assertEquals(42, sut.getNumberOfCoins());
  }

  @Test
  void constructor_EvenSizeGiven_PlacedAtCenterAndValuesSaved() {
    World.setSize(10, 20);

    var sut = new RobotWithOffspring(9, 19, Direction.LEFT, 42);

    assertEquals(4, sut.getX());
    assertEquals(9, sut.getY());
    assertEquals(Direction.LEFT, sut.getDirection());
    assertEquals(42, sut.getNumberOfCoins());
  }

  @Test
  void offspringIsInitialized_Initialized_ReturnsTrue() {
    World.setSize(5, 5);

    var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
    sut.initOffspring(Direction.UP, 0);
    var result = sut.offspringIsInitialized();

    assertTrue(result);
  }

  @Test
  void offspringIsInitialized_NotInitialized_ReturnsFalse() {
    World.setSize(5, 5);

    var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
    var result = sut.offspringIsInitialized();

    assertFalse(result);
  }

  @Test
  void getXOfOffspring_NotInitialized_ThrowsNullPointerException() {
    World.setSize(5, 5);

    var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);

    var exception = assertThrows(NullPointerException.class, () -> {
      sut.getXOfOffspring();
    });

    assertEquals("Cannot invoke \"fopbot.Robot.getX()\" because \"this.offspring\" is null", exception.getMessage());
  }

  @Test
  void getXOfOffspring_Initialized_ReturnsCoordinate() {
    World.setSize(5, 5);

    var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
    sut.initOffspring(Direction.UP, 0);
    var result = sut.getXOfOffspring();

    assertEquals(2, result);
  }
}
