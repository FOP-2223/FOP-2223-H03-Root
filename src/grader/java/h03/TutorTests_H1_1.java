package h03;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h03.ArgumentsProviders.WorldArgumentsProvider;
import h03.ReflectionUtils.AttributeMatcher;
import h03.ReflectionUtils.ClassTester;
import h03.ReflectionUtils.ParameterMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h03")
@DisplayName("H1.1")
public class TutorTests_H1_1 {
    final String class_name = "RobotWithOffspring";

    @BeforeEach
    public void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);
    }

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    public void t01() {
        robotWithOffspringCT.verify(1);
    }

    @Test
    @DisplayName("2 | Attribute numberOfColumnsOfWorld und numberOfRowsOfWorld")
    public void t02() {
        robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfColumnsOfWorld", 1, Modifier.PRIVATE | Modifier.FINAL,
                int.class));
        robotWithOffspringCT.resolve().resolveAttribute(
            new AttributeMatcher("numberOfRowsOfWorld", 1, Modifier.PRIVATE | Modifier.FINAL,
                int.class));
        // TODO: final keyword zulassen? derzeit verboten und Punktabzug, wenn nicht explizit gefordert
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/parameters.csv")
    @ArgumentsSource(WorldArgumentsProvider.class)
    @DisplayName("3 | Konstruktor")
    @SuppressWarnings("unchecked")
    public void t03(int worldWidth, int worldHeight) throws NoSuchFieldException {
        var numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", 0.8, int.class);
        var numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", 0.8, int.class);
        var directionParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", 0.8, Direction.class);
        var numberOfCoinsOfWorldParameterMatcher = new ParameterMatcher("numberOfCoins", 0.8, int.class);

        Constructor<Object> constructor =
            (Constructor<Object>) robotWithOffspringCT.assureClassResolved().resolveConstructor(numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher, directionParameterMatcher, numberOfCoinsOfWorldParameterMatcher);
        ((ClassTester<Object>) robotWithOffspringCT).assertConstructorValid(constructor, Modifier.PUBLIC,
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher, directionParameterMatcher,
            numberOfCoinsOfWorldParameterMatcher);

        Field numberOfColumnsOfWorldField = numberOfColumnsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfColumnsOfWorld", 0.8,
                Modifier.PRIVATE | Modifier.FINAL, int.class));

        Field numberOfRowsOfWorldField = robotWithOffspringCT
            .resolveAttribute(new AttributeMatcher("numberOfRowsOfWorld", 0.8,
                Modifier.PRIVATE | Modifier.FINAL, int.class));

        Field directionField = Robot.class.getDeclaredField("direction");
        Field numberOfCoinsWorldField = Robot.class.getDeclaredField("numberOfCoins");
        Field xField = FieldEntity.class.getDeclaredField("x");
        Field yField = FieldEntity.class.getDeclaredField("y");

        // TODO: besser parametrisieren? Direction und numberOfCoins relativ statisch derzeit
        // Scenario 1
        ((ClassTester<Object>) robotWithOffspringCT).setClassInstance(assertDoesNotThrow(() -> constructor.newInstance(worldWidth, worldHeight, Direction.DOWN, 29)));
        robotWithOffspringCT.assertFieldEquals(numberOfColumnsOfWorldField, worldWidth);
        robotWithOffspringCT.assertFieldEquals(numberOfRowsOfWorldField, worldHeight);
        robotWithOffspringCT.assertFieldEquals(directionField, Direction.DOWN);
        robotWithOffspringCT.assertFieldEquals(numberOfCoinsWorldField, 29);
        robotWithOffspringCT.assertFieldEquals(xField, worldWidth / 2);
        robotWithOffspringCT.assertFieldEquals(yField, worldHeight / 2);

        // Scenario 2
        ((ClassTester<Object>) robotWithOffspringCT).setClassInstance(assertDoesNotThrow(() -> constructor.newInstance(worldWidth, worldHeight, Direction.LEFT, 1982)));
        robotWithOffspringCT.assertFieldEquals(numberOfColumnsOfWorldField, worldWidth);
        robotWithOffspringCT.assertFieldEquals(numberOfRowsOfWorldField, worldHeight);
        robotWithOffspringCT.assertFieldEquals(directionField, Direction.LEFT);
        robotWithOffspringCT.assertFieldEquals(numberOfCoinsWorldField, 1982);
        robotWithOffspringCT.assertFieldEquals(xField, worldWidth / 2);
        robotWithOffspringCT.assertFieldEquals(yField, worldHeight / 2);

        // Scenario 3
        ((ClassTester<Object>) robotWithOffspringCT).setClassInstance(assertDoesNotThrow(() -> constructor.newInstance(worldWidth, worldHeight, Direction.UP, 0)));
        robotWithOffspringCT.assertFieldEquals(numberOfColumnsOfWorldField, worldWidth);
        robotWithOffspringCT.assertFieldEquals(numberOfRowsOfWorldField, worldHeight);
        robotWithOffspringCT.assertFieldEquals(directionField, Direction.UP);
        robotWithOffspringCT.assertFieldEquals(numberOfCoinsWorldField, 0);
        robotWithOffspringCT.assertFieldEquals(xField, worldWidth / 2);
        robotWithOffspringCT.assertFieldEquals(yField, worldHeight / 2);
    }
}
