package h03;

import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static h03.H03_Class_Testers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

@TestForSubmission
@ExtendWith(TestCycleResolver.class)
public class TutorTests_H1_1 {

    public static boolean classTransformed = false;
    public static Class<?> sut;

    public TutorTests_H1_1(TestCycle testCycle) {
        if (!classTransformed) {
            String className = robotWithOffspringCT.findClass().getName();
            sut = testCycle.getClassLoader()
                .loadClass(className, ClassTransformer.injectSuperclass(className, TutorRobot.class.getName()));
            classTransformed = true;
        }
    }

    @BeforeAll
    public static void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);
    }

    // DONE
    @Test
    @DisplayName("Klasse \"RobotWithOffspring\" wurde korrekt deklariert.")
    public void classRobotWithOffspringDeclaredCorrectly() {
        robotWithOffspringCT.verify(MIN_SIM);
    }

    // DONE
    @Test
    @DisplayName("Attribut \"numberOfColumnsOfWorld\" wurde korrekt deklariert.")
    public void numberOfColumnsOfWorldDeclaredCorrectly() {
        var attribute = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfColumnsOfWorld", MIN_SIM, Modifier.PRIVATE | Modifier.FINAL,
                int.class));

        assertFalse(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"numberOfColumnsOfWorld\" ist ein Array, sollte aber kein Array sein.");
    }

    // DONE
    @Test
    @DisplayName("Attribut \"numberOfRowsOfWorld\" wurde korrekt deklariert.")
    public void numberOfRowsOfWorldDeclaredCorrectly() {
        var attribute = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfRowsOfWorld", MIN_SIM, Modifier.PRIVATE | Modifier.FINAL,
                int.class));

        assertFalse(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"numberOfRowsOfWorld\" ist ein Array, sollte aber kein Array sein.");
    }

    // DONE
    @Test
    @DisplayName("Konstruktor von \"RobotWithOffspring\" wurde korrekt deklariert.")
    @SuppressWarnings("unchecked")
    public void constructorDeclaredCorrectly() {
        var numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", MIN_SIM_PARAM, int.class);
        var numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", MIN_SIM_PARAM, int.class);
        var directionParameterMatcher = new ParameterMatcher("direction", MIN_SIM_PARAM, Direction.class);
        var numberOfCoinsOfWorldParameterMatcher = new ParameterMatcher("numberOfCoins", MIN_SIM_PARAM, int.class);

        var constructor = (Constructor<Object>) robotWithOffspringCT.assureClassResolved().resolveConstructor(
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher,
            directionParameterMatcher, numberOfCoinsOfWorldParameterMatcher);

        ((ClassTester<Object>) robotWithOffspringCT).assertConstructorValid(constructor, Modifier.PUBLIC,
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher,
            directionParameterMatcher, numberOfCoinsOfWorldParameterMatcher);
    }

    // DONE
    @ParameterizedTest
    @CsvFileSource(resources = "/TutorTests_H1_1-constructorSetsAttributesCorrectly.csv", numLinesToSkip = 1)
    @DisplayName("Konstruktor setzt \"numberOfColumnsOfWorld\" und \"numberOfRowsOfWorld\" korrekt.")
    @SuppressWarnings("unchecked")
    public void constructorSetsAttributesCorrectly(int numberOfColumnsOfWorld, int numberOfRowsOfWorld) throws IllegalAccessException {
        var numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", MIN_SIM_PARAM, int.class);
        var numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", MIN_SIM_PARAM, int.class);
        var directionParameterMatcher = new ParameterMatcher("direction", MIN_SIM_PARAM, Direction.class);
        var numberOfCoinsOfWorldParameterMatcher = new ParameterMatcher("numberOfCoins", MIN_SIM_PARAM, int.class);

        var constructor = (Constructor<Object>) robotWithOffspringCT.assureClassResolved().resolveConstructor(
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher,
            directionParameterMatcher, numberOfCoinsOfWorldParameterMatcher);

        var newInstance = assertDoesNotThrow(() -> constructor.newInstance(
                numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.UP, 0),
            String.format("Der Konstruktor von \"%s\" wirft eine unerwartete Exception.",
                robotWithOffspringCT.assureClassResolved().getTheClass().getName()));
        ((ClassTester<Object>) robotWithOffspringCT).setClassInstance(newInstance);

        var numberOfColumnsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfColumnsOfWorld", MIN_SIM, int.class));
        assertFalse(numberOfColumnsOfWorldField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                numberOfColumnsOfWorldField.getName()));

        var numberOfRowsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfRowsOfWorld", MIN_SIM, int.class));
        assertFalse(numberOfRowsOfWorldField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                numberOfRowsOfWorldField.getName()));

        numberOfColumnsOfWorldField.setAccessible(true);
        var actualNumberOfColumns = numberOfColumnsOfWorldField.get(robotWithOffspringCT.getClassInstance());
        assertEquals(actualNumberOfColumns, numberOfColumnsOfWorld, "Die Anzahl der Spalten wird nicht korrekt gesetzt.");

        numberOfRowsOfWorldField.setAccessible(true);
        var actualNumberOfRows = numberOfRowsOfWorldField.get(robotWithOffspringCT.getClassInstance());
        assertEquals(actualNumberOfRows, numberOfRowsOfWorld, "Die Anzahl der Zeilen wird nicht korrekt gesetzt.");
    }

    // DONE
    @ParameterizedTest
    @CsvFileSource(resources = "/TutorTests_H1_1-constructorCallsSuperConstructorCorrectly.csv", numLinesToSkip = 1)
    @DisplayName("Konstruktor ruft super-Konstruktor von \"Robot\" korrekt auf.")
    public void constructorCallsSuperConstructorCorrectly(int numberOfColumnsOfWorld, int numberOfRowsOfWorld,
                                                          Direction direction, int numberOfCoins,
                                                          int expectedX, int expectedY) {
        final var className = robotWithOffspringCT.assureClassResolved().getTheClass().getName();
        var constructor = assertDoesNotThrow(() -> sut.getConstructor(int.class, int.class, Direction.class, int.class),
            String.format("Der Konstruktor der Klasse  \"%s\" wurde nicht korrekt deklariert.", className));
        TutorRobot robot;

        try {
            robot = (TutorRobot) constructor.newInstance(numberOfColumnsOfWorld,
                numberOfRowsOfWorld, direction, numberOfCoins);
        } catch (Throwable e) {
            fail(String.format("Der Konstruktor von \"%s\" wirft eine unerwartete Exception: %s", className, e.getCause()));
            return;
        }

        assertEquals(1, robot.callsToTutorRobotConstructorIntIntDirectionInt.size(), "Der super-Konstruktor der Klasse " +
            "\"Robot\" wurde nicht aufgerufen.");
        assertEquals(expectedX, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getX(),
            String.format("Beim Aufruf des super-Konstruktors wird der x-Wert für den Ausgangswert %s für die " +
                "numberOfColumnsOfWorld nicht korrekt gesetzt.", numberOfColumnsOfWorld));
        assertEquals(expectedY, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getY(),
            String.format("Beim Aufruf des super-Konstruktors wird der y-Wert für den Ausgangswert %s für die " +
                "numberOfRowsOfWorld nicht korrekt gesetzt.", numberOfRowsOfWorld));
        assertEquals(direction, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getDirection(),
            "Beim Aufruf des super-Konstruktors wird die Direction nicht korrekt gesetzt.");
        assertEquals(numberOfCoins, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getNumberOfCoins(),
            "Beim Aufruf des super-Konstruktors wird die Anzahl Münzen nicht korrekt gesetzt.");
    }
}
