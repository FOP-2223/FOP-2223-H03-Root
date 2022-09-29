package h03;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h03.ArgumentsProviders.WorldArgumentsProvider;
import h03.ReflectionUtils.AttributeMatcher;
import h03.ReflectionUtils.ClassTester;
import h03.ReflectionUtils.MethodTester;
import h03.ReflectionUtils.ParameterMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestForSubmission("h03")
@DisplayName("H3.1")
public class TutorTests_H3_1 {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
        World.setSize(500, 500);
    }

    @Test
    @DisplayName("1 | Attribut robots")
    public void t01() {
        twinRobotsCT.resolve().resolveAttribute(
            new AttributeMatcher("robots", 1, Modifier.PRIVATE | Modifier.FINAL,
                robotWithOffspringCT.assureClassResolved().getClass()));
    }

    @ParameterizedTest
    @CsvSource({"12,32"})
    @DisplayName("2 | Konstruktor")
    @SuppressWarnings("unchecked")
    public void t02(int numberOfColumnsOfWorld, int numberOfRowsOfWorld) {
        var numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", 0.8, int.class);
        var numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", 0.8, int.class);

        Constructor<Object> constructor =
            (Constructor<Object>) twinRobotsCT.assureClassResolved().resolveConstructor(numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher);

        ((ClassTester<Object>) twinRobotsCT).assertConstructorValid(constructor, Modifier.PUBLIC,
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher);

        Field robotsField = twinRobotsCT
            .resolveAttribute(new AttributeMatcher("robots", 0.8, Modifier.PRIVATE | Modifier.FINAL,
                robotWithOffspringCT.assureClassResolved().getTheClass()));

        ((ClassTester<Object>) twinRobotsCT).setClassInstance(assertDoesNotThrow(() -> constructor.newInstance(numberOfColumnsOfWorld, numberOfRowsOfWorld)));

        var twinRobotsInstance = twinRobotsCT.getClassInstance();
        assertDoesNotThrow(() -> robotsField.setAccessible(true));

        var field = assertDoesNotThrow(() -> robotsField.get(twinRobotsInstance));
        assertEquals(2, Array.getLength(field), "Der Array \"robots\" hat nicht die Größe 2.");

        var firstRobot = Array.get(field, 0);
        assertEquals(robotWithOffspringCT.assureClassResolved().getTheClass(), firstRobot.getClass(), "Das Objekt am " +
            "Index 0 im Array \"robots\" ist nicht vom Typ \"RobotWithOffspring\"");

//        var mt = new MethodTester(robotWithOffspringCT, "initOffSpring", 0.8,
//            Modifier.PUBLIC,
//            void.class, new ArrayList<>(List.of(new ParameterMatcher("direction", 0.8, Direction.class), new
//            ParameterMatcher("numberOfCoins", 0.8, int.class))));
//        getAverageSpeeedMT.resolveMethod();
//
//        assertDoesNotThrow(
//            () -> when(getAverageSpeeedMT.getTheMethod().invoke(fast_mammalia, ArgumentMatchers.anyDouble()))
//                .thenReturn(10d),
//            "Could not Overwrite Method.");

        var secondRobot = Array.get(field, 1);
        assertEquals(robotWithOffspring2CT.assureClassResolved().getTheClass(), secondRobot.getClass(), "Das Objekt " +
            "am Index 1 im Array \"robots\" ist nicht vom Typ \"RobotWithOffspring2\"");

        // TODO: fertig schreiben

        //        robotWithOffspringCT.assertFieldEquals(numberOfColumnsOfWorldField, worldWidth);
//        robotWithOffspringCT.assertFieldEquals(numberOfRowsOfWorldField, worldHeight);
//        robotWithOffspringCT.assertFieldEquals(directionField, Direction.DOWN);
//        robotWithOffspringCT.assertFieldEquals(numberOfCoinsWorldField, 29);
//        robotWithOffspringCT.assertFieldEquals(xField, worldWidth / 2);
//        robotWithOffspringCT.assertFieldEquals(yField, worldHeight / 2);
    }
}
