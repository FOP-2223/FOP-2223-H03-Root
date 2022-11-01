package h03;

import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.robotWithOffspring2CT;
import static h03.H03_Class_Testers.robotWithOffspringCT;
import static h03.H03_Class_Testers.twinRobotsCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestForSubmission
@DisplayName("H3.1")
public class TutorTests_H3_1 {
    @BeforeAll
    public static void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);
    }

    // DONE
    @Test
    @DisplayName("Attribut \"robots\" wurde korrekt deklariert.")
    public void robotsDeclaredCorrectly() {
        var attribute = twinRobotsCT.resolve().resolveAttribute(
            new AttributeMatcher("robots", 1, Modifier.PRIVATE | Modifier.FINAL,
                robotWithOffspringCT.assureClassResolved().getClass()));

        assertTrue(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"robots\" ist kein Array, sollte aber ein Array sein.");
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

        assertTrue(robotsField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist kein Array, sollte aber ein Array sein.", robotsField.getName()));

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

    // DONE
    @Test
    @DisplayName("Methode \"getRobotByIndex\" wurde korrekt deklariert.")
    public void getRobotByIndexDeclaredCorrectly() {
        new MethodTester(twinRobotsCT.resolve(), "getRobotByIndex", 1, Modifier.PUBLIC,
            robotWithOffspringCT.assureClassResolved().getTheClass(),
            new ArrayList<>(List.of(new ParameterMatcher("index", 0, int.class)))).verify();
    }

    // TODO: replace hard coded classes. What happens if the classes don't exist or have different constructors?
    @Test
    @DisplayName("Methode \"getRobotByIndex\" wurde korrekt implementiert.")
    public void getRobotByIndexImplementedCorrectly() throws IllegalAccessException {
        Field robotsField = twinRobotsCT.assureClassResolved()
            .resolveAttribute(new AttributeMatcher("robots", 0.8, robotWithOffspringCT.assureClassResolved().getTheClass()));
        assertTrue(robotsField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist kein Array, sollte aber ein Array sein.", robotsField.getName()));

        robotsField.setAccessible(true);
        Object twinRobotsInstance = twinRobotsCT.resolve().getClassInstance();
        var array = new RobotWithOffspring[2];
        array[0] = new RobotWithOffspring(12, 33, Direction.LEFT, 68);
        array[1] = new RobotWithOffspring2(53, 76, Direction.RIGHT, 361);
        robotsField.set(twinRobotsInstance, array);

        var methodTester = new MethodTester(twinRobotsCT.assureClassResolved(), "getRobotByIndex", 0.8, Modifier.PUBLIC,
            robotWithOffspringCT.assureClassResolved().getTheClass(),
            new ArrayList<>(List.of(new ParameterMatcher("index", 0, int.class)))).verify();

        var actualFirstRobot = methodTester.invoke(0);
        var actualSecondRobot = methodTester.invoke(1);

        assertEquals(array[0], actualFirstRobot,
            String.format("Die Methode \"%s\" gibt für Index 0 nicht das korrekte %s-Objekt aus dem Array %s zurück.",
                methodTester.getTheMethod().getName(), robotWithOffspringCT.getTheClass().getName(), robotsField.getName()));
        assertEquals(array[1], actualSecondRobot,
            String.format("Die Methode \"%s\" gibt für Index 1 nicht das korrekte %s-Objekt aus dem Array %s zurück.",
                methodTester.getTheMethod().getName(), robotWithOffspringCT.getTheClass().getName(), robotsField.getName()));
    }

    // DONE
    @Test
    @DisplayName("Methode \"addToDirectionOfBothOffsprings\" wurde korrekt deklariert.")
    public void addToDirectionOfBothOffspringsDeclaredCorrectly() {
        new MethodTester(twinRobotsCT.resolve(), "addToDirectionOfBothOffsprings", 1, Modifier.PUBLIC,
            void.class,
            new ArrayList<>(List.of(new ParameterMatcher("directionToBeAdded", 0, int.class)))).verify();
    }
}
