package h03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fopbot.Direction;
import fopbot.World;

class RobotWithOffspring2Test {
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    void initOffspring_GetDirectionOfOffspring_Parameterized_CorrectDirectionSet(int directionNumber) {
        World.setSize(5, 5);
        World.setDelay(0);

        var expectedDirection = Direction.values()[directionNumber];

        var sut = new RobotWithOffspring2(5, 5, Direction.UP, 0);
        sut.initOffspring(expectedDirection, 0);

        var actualDirection = sut.getDirectionOfOffspring();

        assertEquals(expectedDirection, actualDirection);
    }
}
