package h03;

import fopbot.Direction;
import fopbot.World;
import org.jetbrains.annotations.NotNull;
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

import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestForSubmission
@DisplayName("H1.1")
public class TutorTests_H1_1 {
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
        robotWithOffspringCT.verify(1);
    }

    // DONE
    @Test
    @DisplayName("Attribut \"numberOfColumnsOfWorld\" wurde korrekt deklariert.")
    public void numberOfColumnsOfWorldDeclaredCorrectly() {
        var attribute = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfColumnsOfWorld", 1, Modifier.PRIVATE | Modifier.FINAL,
                int.class));

        assertFalse(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"numberOfColumnsOfWorld\" ist ein Array, sollte aber kein Array sein.");
    }

    // DONE
    @Test
    @DisplayName("Attribut \"numberOfRowsOfWorld\" wurde korrekt deklariert.")
    public void numberOfRowsOfWorldDeclaredCorrectly() {
        var attribute = robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfRowsOfWorld", 1, Modifier.PRIVATE | Modifier.FINAL,
                int.class));

        assertFalse(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"numberOfRowsOfWorld\" ist ein Array, sollte aber kein Array sein.");
    }

    // DONE
    @Test
    @DisplayName("Konstruktor von \"RobotWithOffspring\" wurde korrekt deklariert.")
    @SuppressWarnings("unchecked")
    public void constructorDeclaredCorrectly() {
        var numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", 0.8, int.class);
        var numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", 0.8, int.class);
        var directionParameterMatcher = new ParameterMatcher("direction", 0.8, Direction.class);
        var numberOfCoinsOfWorldParameterMatcher = new ParameterMatcher("numberOfCoins", 0.8, int.class);

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
    public void constructorSetsAttributesCorrectly(int numberOfColumnsOfWorld, int numberOfRowsOfWorld) {
        var numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", 0.8, int.class);
        var numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", 0.8, int.class);
        var directionParameterMatcher = new ParameterMatcher("direction", 0.8, Direction.class);
        var numberOfCoinsOfWorldParameterMatcher = new ParameterMatcher("numberOfCoins", 0.8, int.class);

        var constructor = (Constructor<Object>) robotWithOffspringCT.assureClassResolved().resolveConstructor(
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher,
            directionParameterMatcher, numberOfCoinsOfWorldParameterMatcher);

        var newInstance = assertDoesNotThrow(() -> constructor.newInstance(
                numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.UP, 0),
            String.format("Der Konstruktor von \"%s\" wirft eine unerwartete Exception.",
                robotWithOffspringCT.assureClassResolved().getTheClass().getName()));
        ((ClassTester<Object>) robotWithOffspringCT).setClassInstance(newInstance);

        var numberOfColumnsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfColumnsOfWorld", 0.8, int.class));
        assertFalse(numberOfColumnsOfWorldField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                numberOfColumnsOfWorldField.getName()));

        var numberOfRowsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfRowsOfWorld", 0.8, int.class));
        assertFalse(numberOfRowsOfWorldField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                numberOfRowsOfWorldField.getName()));

        robotWithOffspringCT.assertFieldEquals(numberOfColumnsOfWorldField, numberOfColumnsOfWorld);
        robotWithOffspringCT.assertFieldEquals(numberOfRowsOfWorldField, numberOfRowsOfWorld);
    }

    // TODO: Review. Is this the only sequence of opcodes that can occur or are there different solutions possible?
    // Should we maybe just test for the result? But then students could ignore the constructor and just set the attributes.
    @ParameterizedTest
    @CsvFileSource(resources = "/TutorTests_H1_1-constructorCallsSuperConstructorCorrectly.csv", numLinesToSkip = 1)
    @DisplayName("Konstruktor ruft super-Konstruktor von \"Robot\" korrekt auf.")
    @ExtendWith(TestCycleResolver.class)
    public void constructorCallsSuperConstructorCorrectly(int x, int y, Direction direction, int numberOfCoins,
                                                          int expectedX, int expectedY, @NotNull TestCycle testCycle) {
        final var className = robotWithOffspringCT.assureClassResolved().getTheClass().getName();
        var sut = testCycle.getClassLoader().loadClass(className,
            ClassTransformer.injectSuperclass(className, TutorRobot.class.getName()));

        var constructor = assertDoesNotThrow(() -> sut.getConstructor(int.class, int.class, Direction.class, int.class),
            String.format("Der Konstruktor der Klasse  \"%s\" wurde nicht korrekt deklariert.", className));

        TutorRobot robot = (TutorRobot) assertDoesNotThrow(() -> constructor.newInstance(x, y, direction, numberOfCoins),
            String.format("Der Konstruktor von \"%s\" wirft eine unerwartete Exception.", className));

        assertEquals(1, robot.callsToTutorRobotConstructorIntIntDirectionInt.size());
        assertEquals(expectedX, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getX());
        assertEquals(expectedY, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getY());
        assertEquals(direction, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getDirection());
        assertEquals(numberOfCoins, robot.callsToTutorRobotConstructorIntIntDirectionInt.get(0).getNumberOfCoins());
    }
}
