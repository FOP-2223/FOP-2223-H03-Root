package h03;

import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RobotWithOffspringTest {
  @ParameterizedTest
  @CsvSource({ "9,19,4,9", "10,20,5,10" })
  void constructor_Parameterized_PlacedAtCenterAndValuesSaved(String widthString, String heightString, String xString,
      String yString) {
    int width = Integer.parseInt(widthString);
    int height = Integer.parseInt(heightString);
    int x = Integer.parseInt(xString);
    int y = Integer.parseInt(yString);

    World.setSize(width, height);

    var sut = new RobotWithOffspring(width, height, Direction.LEFT, 0);

    assertEquals(x, sut.getX());
    assertEquals(y, sut.getY());
    assertEquals(Direction.LEFT, sut.getDirection());
    assertEquals(0, sut.getNumberOfCoins());
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

  @ParameterizedTest
  @CsvSource({ "0,1", "1,2", "2,3", "3,0", "4,1", "-1,0", "-2,3", "-3,2", "-4,1", "-5,0" })
  void addToDirectionOfOffspring_Parameterized_CorrectDirectionSet(String addedDirectionString,
      String expectedDirectionString) {
    World.setSize(5, 5);

    var added = Integer.parseInt(addedDirectionString);
    var expected = Integer.parseInt(expectedDirectionString);

    var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
    sut.initOffspring(Direction.RIGHT, 0);
    sut.addToDirectionOfOffspring(added);

    var actualDirection = sut.getDirectionOfOffspring();
    var expectedDirection = Direction.values()[expected];

    assertEquals(expectedDirection, actualDirection);
  }
}
