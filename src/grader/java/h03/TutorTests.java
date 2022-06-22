package h03;

import fopbot.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h03")
public class TutorTests {
    @Nested
    class RobotWithOffspringTest {
        @ParameterizedTest
        @CsvSource({"9,19,4,9", "10,20,5,10"})
        void constructor_Parameterized_PlacedAtCenterAndValuesSaved(String widthString, String heightString, String xString,
                                                                    String yString) {
            int width = Integer.parseInt(widthString);
            int height = Integer.parseInt(heightString);
            int x = Integer.parseInt(xString);
            int y = Integer.parseInt(yString);

            World.setSize(width, height);
            World.setDelay(0);

            var sut = new RobotWithOffspring(width, height, Direction.LEFT, 0);

            assertEquals(x, sut.getX());
            assertEquals(y, sut.getY());
            assertEquals(Direction.LEFT, sut.getDirection());
            assertEquals(0, sut.getNumberOfCoins());
        }

        @Test
        void offspringIsInitialized_Initialized_ReturnsTrue() {
            World.setSize(5, 5);
            World.setDelay(0);

            var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
            sut.initOffspring(Direction.UP, 0);
            var result = sut.offspringIsInitialized();

            assertTrue(result);
        }

        @Test
        void offspringIsInitialized_NotInitialized_ReturnsFalse() {
            World.setSize(5, 5);
            World.setDelay(0);

            var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
            var result = sut.offspringIsInitialized();

            assertFalse(result);
        }

        @Test
        void getXOfOffspring_NotInitialized_ThrowsNullPointerException() {
            World.setSize(5, 5);
            World.setDelay(0);

            var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);

            var exception = assertThrows(NullPointerException.class, () -> {
                sut.getXOfOffspring();
            });

            assertEquals("Cannot invoke \"fopbot.Robot.getX()\" because \"this.offspring\" is null",
                exception.getMessage());
        }

        @Test
        void getXOfOffspring_Initialized_ReturnsCoordinate() {
            World.setSize(5, 5);
            World.setDelay(0);

            var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
            sut.initOffspring(Direction.UP, 0);
            var result = sut.getXOfOffspring();

            assertEquals(2, result);
        }

        @ParameterizedTest
        @CsvSource({"5,1,3", "5,0,2", "5,-5,0", "5,15,4"})
        void addToXOffOffspring_belowLimit_CorrectPositionSet(String numberOfColumnsOfWorldString, String addedXString, String expectedXString) {
            var numberOfColumnsOfWorld = Integer.parseInt(numberOfColumnsOfWorldString);
            var addedX = Integer.parseInt(addedXString);
            var expectedX = Integer.parseInt(expectedXString);

            World.setSize(numberOfColumnsOfWorld, 5);
            World.setDelay(0);

            var sut = new RobotWithOffspring(numberOfColumnsOfWorld, 5, Direction.UP, 0);
            sut.initOffspring(Direction.UP, 0);
            sut.addToXOfOffspring(addedX);

            assertEquals(expectedX, sut.getXOfOffspring());
            assertEquals(2, sut.getYOfOffspring());
        }

        @ParameterizedTest
        @CsvSource({"5,1,3", "5,0,2", "5,-5,0", "5,15,4"})
        void addToYOffOffspring_belowLimit_CorrectPositionSet(String numberOfRowsOfWorldString, String addedYString, String expectedYString) {
            var numberOfRowsOfWorld = Integer.parseInt(numberOfRowsOfWorldString);
            var addedY = Integer.parseInt(addedYString);
            var expectedY = Integer.parseInt(expectedYString);

            World.setSize(5, numberOfRowsOfWorld);
            World.setDelay(0);

            var sut = new RobotWithOffspring(5, numberOfRowsOfWorld, Direction.UP, 0);
            sut.initOffspring(Direction.UP, 0);
            sut.addToYOfOffspring(addedY);

            assertEquals(expectedY, sut.getYOfOffspring());
            assertEquals(2, sut.getXOfOffspring());
        }

        @ParameterizedTest
        @CsvSource({"0,1", "1,2", "2,3", "3,0", "4,1", "-1,0", "-2,3", "-3,2", "-4,1", "-5,0"})
        void addToDirectionOfOffspring_Parameterized_CorrectDirectionSet(String addedDirectionString,
                                                                         String expectedDirectionString) {
            World.setSize(5, 5);
            World.setDelay(0);

            var added = Integer.parseInt(addedDirectionString);
            var expected = Integer.parseInt(expectedDirectionString);

            var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
            sut.initOffspring(Direction.RIGHT, 0);
            sut.addToDirectionOfOffspring(added);

            var actualDirection = sut.getDirectionOfOffspring();
            var expectedDirection = Direction.values()[expected];

            assertEquals(expectedDirection, actualDirection);
        }

        @Test
        void getNumberOfCoinsOfOffspring_Initialized_ReturnsCorrectNumberOfCoins() {
            World.setSize(5, 5);
            World.setDelay(0);

            var sut = new RobotWithOffspring(5, 5, Direction.UP, 0);
            sut.initOffspring(Direction.RIGHT, 42);

            assertEquals(42, sut.getNumberOfCoinsOfOffspring());
        }
    }

    @Nested
    class RobotWithOffspring2Test {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void getDirectionOfOffspring_Initialized_CorrectDirectionSet(int directionNumber) {
            World.setSize(5, 5);
            World.setDelay(0);

            var expectedDirection = Direction.values()[directionNumber];

            var sut = new RobotWithOffspring2(5, 5, Direction.UP, 0);
            sut.initOffspring(expectedDirection, 0);

            var actualDirection = sut.getDirectionOfOffspring();

            assertEquals(expectedDirection, actualDirection);
        }

        @ParameterizedTest
        @CsvSource({"0,0", "1,1", "2,2", "3,3", "4,0", "5,1", "6,2", "-1,3", "-2,2", "-3,1", "-4,0", "-5,3", "42,2"})
        void addToDirectionOfOffspring_DirectionAdded_CorrectDirectionSet(String directionAddedString, String expectedDirectionString) {
            World.setSize(5, 5);
            World.setDelay(0);

            var directionAdded = Integer.parseInt(directionAddedString);
            var expectedDirection = Direction.values()[Integer.parseInt(expectedDirectionString)];

            var sut = new RobotWithOffspring2(5, 5, Direction.UP, 0);
            sut.initOffspring(Direction.UP, 0);
            sut.addToDirectionOfOffspring(directionAdded);

            var actualDirection = sut.getDirectionOfOffspring();

            assertEquals(expectedDirection, actualDirection);
        }

        @ParameterizedTest
        @CsvSource({"0,1", "1,2", "2,3", "3,0", "4,1", "-1,0", "-2,3", "-3,2", "-4,1", "-5,0"})
        void addToDirectionOfOffspring_InitializedDifferentlyAndDirectionAdded_CorrectDirectionSet(String addedDirectionString,
                                                                                                   String expectedDirectionString) {
            World.setSize(5, 5);
            World.setDelay(0);

            var added = Integer.parseInt(addedDirectionString);
            var expected = Integer.parseInt(expectedDirectionString);

            var sut = new RobotWithOffspring2(5, 5, Direction.UP, 0);
            sut.initOffspring(Direction.RIGHT, 0);
            sut.addToDirectionOfOffspring(added);

            var actualDirection = sut.getDirectionOfOffspring();
            var expectedDirection = Direction.values()[expected];

            assertEquals(expectedDirection, actualDirection);
        }
    }

    @Nested
    class TwinRobotsTest {
        @Test
        void constructor_OffspringsCorrectlyInitialized() {
            World.setSize(5, 5);
            World.setDelay(0);

            var sut = new TwinRobots(5, 5);

            var robot1 = sut.getRobotByIndex(0);
            var robot2 = sut.getRobotByIndex(1);

            assertEquals(2, robot1.getXOfOffspring());
            assertEquals(2, robot1.getYOfOffspring());
            assertEquals(Direction.LEFT, robot1.getDirectionOfOffspring());

            assertEquals(2, robot2.getXOfOffspring());
            assertEquals(2, robot2.getYOfOffspring());
            assertEquals(Direction.LEFT, robot2.getDirectionOfOffspring());
        }

        @ParameterizedTest
        @CsvSource({"12,3", "-20,3", "3,2", "42,1"})
        void addToDirectionOfBothOffsprings_PositiveInteger_DirectionCorrectlyAdded(String directionAddedString, String expectedDirectionString) {
            World.setSize(5, 5);
            World.setDelay(0);

            var directionAdded = Integer.parseInt(directionAddedString);
            var expectedDirection = Direction.values()[Integer.parseInt(expectedDirectionString)];

            var sut = new TwinRobots(5, 5);

            var robot1 = sut.getRobotByIndex(0);
            var robot2 = sut.getRobotByIndex(1);
            sut.addToDirectionOfBothOffsprings(directionAdded);

            assertEquals(expectedDirection, robot1.getDirectionOfOffspring());
            assertEquals(expectedDirection, robot2.getDirectionOfOffspring());
        }
    }
}
