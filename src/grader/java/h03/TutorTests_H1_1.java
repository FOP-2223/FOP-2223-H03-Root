package h03;

import fopbot.Direction;
import fopbot.World;
import h03.transform.RobotWithOffspringTransformer;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.sourcegrade.jagr.launcher.env.Jagr;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestForSubmission
@DisplayName("H1.1")
public class TutorTests_H1_1 {
    @BeforeEach
    public void setup() {
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
        robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfColumnsOfWorld", 1, Modifier.PRIVATE | Modifier.FINAL,
                int.class));
    }

    // DONE
    @Test
    @DisplayName("Attribut \"numberOfRowsOfWorld\" wurde korrekt deklariert.")
    public void numberOfRowsOfWorldDeclaredCorrectly() {
        robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfRowsOfWorld", 1, Modifier.PRIVATE | Modifier.FINAL,
                int.class));
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

    // TODO: Review. Is this the only sequence of opcodes that can occur or are there different solutions possible?
    // Should we maybe just test for the result? But then students could ignore the constructor and just set the attributes.
    @Test
    @DisplayName("Konstruktor ruft super-Konstruktor von \"Robot\" korrekt auf.")
    @ExtendWith(TestCycleResolver.class)
    public void constructorCallsSuperConstructorCorrectly(@NotNull TestCycle testCycle) {
        final var className = robotWithOffspringCT.assureClassResolved().getTheClass().getName();
        try {
            testCycle.getClassLoader().visitClass(className, new RobotWithOffspringTransformer());
            Jagr.Default.getInjector().getInstance(Logger.class).warn("No Error");
        } catch (Throwable t) {
            Jagr.Default.getInjector().getInstance(Logger.class).warn("Error " + t);
        }
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

        var newInstance = assertDoesNotThrow(() -> constructor.newInstance(numberOfColumnsOfWorld, numberOfRowsOfWorld,
                Direction.UP, 0),
            "Der Konstruktor von RobotWithOffspring wirft eine unerwartete Exception.");
        ((ClassTester<Object>) robotWithOffspringCT).setClassInstance(newInstance);

        var numberOfColumnsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfColumnsOfWorld", 0.8, int.class));

        var numberOfRowsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfRowsOfWorld", 0.8, int.class));

        robotWithOffspringCT.assertFieldEquals(numberOfColumnsOfWorldField, numberOfColumnsOfWorld);
        robotWithOffspringCT.assertFieldEquals(numberOfRowsOfWorldField, numberOfRowsOfWorld);
    }
}
