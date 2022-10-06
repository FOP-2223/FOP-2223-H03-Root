package h03;

import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static h03.H03_Class_Testers.robotWithOffspring2CT;
import static h03.H03_Class_Testers.robotWithOffspringCT;
import static h03.H03_Class_Testers.twinRobotsCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission
@DisplayName("H3.1")
public class TutorTests_H3_1 {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);
    }

    // TODO: why is this successful, although the type is an array?
    @Test
    @DisplayName("Attribut \"robots\" wurde korrekt deklariert.")
    public void robotsDeclaredCorrectly() {
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

        var constructor = (Constructor<Object>) twinRobotsCT.assureClassResolved().resolveConstructor(
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher);

        ((ClassTester<Object>) twinRobotsCT).assertConstructorValid(constructor, Modifier.PUBLIC,
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher);

        Field robotsField = twinRobotsCT
            .resolveAttribute(new AttributeMatcher("robots", 0.8, robotWithOffspringCT.assureClassResolved().getTheClass()));

        ((ClassTester<Object>) twinRobotsCT).setClassInstance(
            assertDoesNotThrow(() -> constructor.newInstance(numberOfColumnsOfWorld, numberOfRowsOfWorld)));

        var twinRobotsInstance = twinRobotsCT.getClassInstance();
        assertDoesNotThrow(() -> robotsField.setAccessible(true));

        var field = assertDoesNotThrow(() -> robotsField.get(twinRobotsInstance));
        assertEquals(2, Array.getLength(field), "Der Array \"robots\" hat nicht die Größe 2.");

        var firstRobot = Array.get(field, 0);
        assertEquals(robotWithOffspringCT.assureClassResolved().getTheClass(), firstRobot.getClass(), "Das Objekt am "
            + "Index 0 im Array \"robots\" ist nicht vom Typ \"RobotWithOffspring\"");

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
        assertEquals(robotWithOffspring2CT.assureClassResolved().getTheClass(), secondRobot.getClass(), "Das Objekt "
            + "am Index 1 im Array \"robots\" ist nicht vom Typ \"RobotWithOffspring2\"");

        // TODO: fertig schreiben

        //        robotWithOffspringCT.assertFieldEquals(numberOfColumnsOfWorldField, worldWidth);
        //        robotWithOffspringCT.assertFieldEquals(numberOfRowsOfWorldField, worldHeight);
        //        robotWithOffspringCT.assertFieldEquals(directionField, Direction.DOWN);
        //        robotWithOffspringCT.assertFieldEquals(numberOfCoinsWorldField, 29);
        //        robotWithOffspringCT.assertFieldEquals(xField, worldWidth / 2);
        //        robotWithOffspringCT.assertFieldEquals(yField, worldHeight / 2);
    }
}
