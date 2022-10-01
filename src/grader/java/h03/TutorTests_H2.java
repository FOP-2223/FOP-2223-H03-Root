package h03;

import fopbot.Direction;
import fopbot.World;
import h03.transform.RobotWithOffspring2Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.robotWithOffspring2CT;
import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

// STUBS DONE
@TestForSubmission
@DisplayName("H2")
public class TutorTests_H2 {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
        World.setSize(500, 500);
    }

    // DONE
    @Test
    @DisplayName("Klasse \"RobotWithOffspring2\" wurde korrekt deklariert.")
    @SuppressWarnings("unchecked")
    public void classRobotWithOffspring2DeclaredCorrectly() {
        robotWithOffspring2CT.setSuperClass((Class<Object>) robotWithOffspringCT.assureClassResolved().getTheClass());
        robotWithOffspring2CT.verify(1);
    }

    // DONE
    @Test
    @DisplayName("Attribut \"directionAccu\" wurde korrekt deklariert.")
    public void directionAccuDeclaredCorrectly() {
        robotWithOffspring2CT.resolve().resolveAttribute(
            new AttributeMatcher("directionAccu", 1, Modifier.PRIVATE, int.class));
    }

    @Test
    @DisplayName("Konstruktor von \"RobotWithOffspring2\" wurde korrekt implementiert.")
    @ExtendWith(TestCycleResolver.class)
    public void constructorImplementedCorrectly(TestCycle testCycle) {
        final var className = robotWithOffspring2CT.assureClassResolved().getTheClass().getName();
        testCycle.getClassLoader().visitClass(className, new RobotWithOffspring2Transformer());
        // TODO: überprüfen, dass directionAccu nicht initialisiert wird
        // TODO: Verifizieren, dass der Test im Grader korrekt durchläuft
        // TODO: Error messages, wenn Test fehlschlägt, hinzufügen
    }

    // DONE
    @Test
    @DisplayName("Überschriebene Methode \"initOffspring\" wurde korrekt deklariert.")
    public void initOffspringDeclaredCorrectly() {
        new MethodTester(robotWithOffspring2CT.resolve(), "initOffspring", 1, Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("direction", 0, Direction.class),
                new ParameterMatcher("numberOfCoins", 0, int.class)
            ))).verify();
    }

    @Test
    @DisplayName("Überschriebene Methode \"initOffspring\" ruft den super-Konstruktor korrekt auf.")
    public void initOffspringCallsSuperConstructorCorrectly() {
        // TODO: check that initOffspring calls constructor directly
    }

    // DONE
    @ParameterizedTest
    @CsvSource({"UP,0", "RIGHT,1", "DOWN,2", "LEFT,3"})
    @DisplayName("Überschriebene Methode \"initOffspring\" setzt \"directionAccu\" korrekt.")
    public void initOffspringSetsDirectionAccuCorrectly(Direction direction, int expectedResultValue) throws IllegalAccessException {
        var initOffspringMethod = new MethodTester(robotWithOffspring2CT.resolve(), "initOffspring", 0.8, void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("direction", 0, Direction.class),
                new ParameterMatcher("numberOfCoins", 0, int.class)
            ))).resolveMethod();

        var directionAccuField = robotWithOffspring2CT.resolve().resolveAttribute(
            new AttributeMatcher("directionAccu", 0.8, int.class));

        var robotWithOffspring2Instance = robotWithOffspring2CT.getClassInstance();
        directionAccuField.setAccessible(true);
        directionAccuField.set(robotWithOffspring2Instance, 42);

        assertDoesNotThrow(() -> initOffspringMethod.invoke(robotWithOffspring2Instance, direction, 42),
            "Die Methode \"initOffspring\" wurde nicht korrekt deklariert.");
        assertEquals(expectedResultValue, directionAccuField.get(robotWithOffspring2Instance),
            String.format("Die Methode \"initOffspring\" setzt das Attribut \"directionAccu\" nicht korrekt für Direction %s.",
                direction));
    }

    // DONE
    @Test
    @DisplayName("Methode \"getDirectionFromAccu\" wurde korrekt deklariert.")
    public void getDirectionFromAccuDeclaredCorrectly() {
        new MethodTester(robotWithOffspring2CT.resolve(), "getDirectionFromAccu", 1, Modifier.PRIVATE, Direction.class,
            new ArrayList<>()).verify();
    }

    @Test
    @DisplayName("Methode \"getDirectionFromAccu\" wurde korrekt implementiert.")
    public void getDirectionFromAccuImplementedCorrectly() {
        // TODO: verify that getDirectionFromAccu uses modular arithmetic and returns correct direction according to
        //  directionAccu
    }

    // DONE
    @Test
    @DisplayName("Überschriebene Methode \"getDirectionOfOffspring\" wurde korrekt deklariert.")
    public void getDirectionOfOffspringDeclaredCorrectly() {
        new MethodTester(robotWithOffspring2CT.resolve(), "getDirectionOfOffspring", 1, Modifier.PUBLIC, Direction.class,
            new ArrayList<>()).verify();
    }

    @Test
    @DisplayName("Überschrieben Methode \"getDirectionOfOffspring\" wurde korrekt implementiert.")
    public void getDirectionOfOffspringImplementedCorrectly() {
        // TODO: verify that getDirectionOfOffspring calls getDirectionFromAccu and returns that value
    }

    // DONE
    @Test
    @DisplayName("Überschriebene Methode \"addToDirectionOfOffspring\" wurde korrekt deklariert.")
    public void addToDirectionOfOffspringDeclaredCorrectly() {
        new MethodTester(robotWithOffspring2CT.resolve(), "addToDirectionOfOffspring", 1, Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(new ParameterMatcher("directionToBeAdded", 0, int.class)))).verify();
    }

    @Test
    @DisplayName("Überschrieben Methode \"addToDirectionOfOffspring\" wurde korrekt implementiert.")
    public void addToDirectionOfOffspringImplementedCorrectly() {
        // TODO: verify implementation (see page 9 bottom paragraph)
    }
}
