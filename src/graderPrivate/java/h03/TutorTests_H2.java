package h03;

import fopbot.Direction;
import fopbot.World;
import h03.transform.RobotWithOffspring2TransformerForConstructor;
import h03.transform.RobotWithOffspring2TransformerForGetDirectionOfOffspring;
import h03.transform.RobotWithOffspring2TransformerForInitOffspring;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.robotWithOffspring2CT;
import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// STUBS DONE
@TestForSubmission
@DisplayName("H2")
public class TutorTests_H2 {
    @BeforeAll
    public static void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);
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
        var attribute = robotWithOffspring2CT.resolve().resolveAttribute(
            new AttributeMatcher("directionAccu", 1, Modifier.PRIVATE, int.class));

        assertFalse(attribute.getType().isArray(),
            "Der Datentyp von Attribut \"directionAccu\" ist ein Array, sollte aber kein Array sein.");
    }

    @Test
    @DisplayName("Konstruktor von \"RobotWithOffspring2\" wurde korrekt implementiert.")
    @ExtendWith(TestCycleResolver.class)
    public void constructorImplementedCorrectly(TestCycle testCycle) {
        final var className = robotWithOffspring2CT.assureClassResolved().getTheClass().getName();
        testCycle.getClassLoader().visitClass(className, new RobotWithOffspring2TransformerForConstructor());
        // TODO: überprüfen, dass directionAccu nicht initialisiert wird
        // TODO: Verifizieren, dass der Test im Grader korrekt durchläuft
        // TODO: Error messages, wenn Test fehlschlägt, hinzufügen
        // TODO: im Transformer den Klassennamen dynamisch setzen
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

    // DONE
    @Test
    @DisplayName("Überschriebene Methode \"initOffspring\" ruft den super-Konstruktor korrekt auf.")
    @ExtendWith(TestCycleResolver.class)
    public void initOffspringCallsSuperConstructorCorrectly(@NotNull TestCycle testCycle) {
        final var className = robotWithOffspring2CT.assureClassResolved().getTheClass().getName();
        var initOffspringMethod = new MethodTester(robotWithOffspring2CT.resolve(), "initOffspring", 0.8, void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("direction", 0, Direction.class),
                new ParameterMatcher("numberOfCoins", 0, int.class)
            ))).resolveMethod();

        testCycle.getClassLoader().visitClass(className,
            new RobotWithOffspring2TransformerForInitOffspring(initOffspringMethod.getName()));
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

        assertFalse(directionAccuField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                directionAccuField.getName()));

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

    // DONE
    @ParameterizedTest
    @CsvFileSource(resources = "/TutorTests_H2-getDirectionFromAccuImplementedCorrectly.csv", numLinesToSkip = 1)
    @DisplayName("Methode \"getDirectionFromAccu\" wurde korrekt implementiert.")
    public void getDirectionFromAccuImplementedCorrectly(int directionAccu, Direction expectedDirection) throws IllegalAccessException, InvocationTargetException {
        var directionAccuField = robotWithOffspring2CT.resolve().resolveAttribute(
            new AttributeMatcher("directionAccu", 0.8, int.class));
        assertFalse(directionAccuField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                directionAccuField.getName()));
        directionAccuField.setAccessible(true);

        var getDirectionFromAccuMethod = new MethodTester(robotWithOffspring2CT.resolve(),
            "getDirectionFromAccu", 0.8, Direction.class, new ArrayList<>()).resolveMethod();
        getDirectionFromAccuMethod.setAccessible(true);

        var robotWithOffspring2Instance = robotWithOffspring2CT.getClassInstance();

        directionAccuField.set(robotWithOffspring2Instance, directionAccu);
        var actual = getDirectionFromAccuMethod.invoke(robotWithOffspring2Instance);
        assertEquals(expectedDirection, actual,
            String.format("Die Methode \"%s\" liefert die falsche Direction zurück, wenn %s == %s ist.",
                getDirectionFromAccuMethod.getName(),
                directionAccuField.getName(),
                directionAccu));
    }

    // DONE
    @Test
    @DisplayName("Überschriebene Methode \"getDirectionOfOffspring\" wurde korrekt deklariert.")
    public void getDirectionOfOffspringDeclaredCorrectly() {
        new MethodTester(robotWithOffspring2CT.resolve(), "getDirectionOfOffspring", 1, Modifier.PUBLIC, Direction.class,
            new ArrayList<>()).verify();
    }

    // TODO: mostly done. But what if the method is implemented slightly different
    // maybe its better to exchange the private method getDirectionFromAccu and test it that way
    @Test
    @DisplayName("Überschriebene Methode \"getDirectionOfOffspring\" wurde korrekt implementiert.")
    @ExtendWith(TestCycleResolver.class)
    public void getDirectionOfOffspringImplementedCorrectly(@NotNull TestCycle testCycle) {
        final var className = robotWithOffspring2CT.assureClassResolved().getTheClass().getName();

        var getDirectionOfOffspringMethod = new MethodTester(robotWithOffspring2CT.resolve(), "getDirectionOfOffspring", 0.8,
            Direction.class, null).resolveMethod();

        var getDirectionFromAccuMethod = new MethodTester(robotWithOffspring2CT.resolve(),
            "getDirectionFromAccu", 0.8, Direction.class, new ArrayList<>()).resolveMethod();

        testCycle.getClassLoader().visitClass(className,
            new RobotWithOffspring2TransformerForGetDirectionOfOffspring(getDirectionOfOffspringMethod.getName(), getDirectionFromAccuMethod.getName()));
    }

    // DONE
    @Test
    @DisplayName("Überschriebene Methode \"addToDirectionOfOffspring\" wurde korrekt deklariert.")
    public void addToDirectionOfOffspringDeclaredCorrectly() {
        new MethodTester(robotWithOffspring2CT.resolve(), "addToDirectionOfOffspring", 1, Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(new ParameterMatcher("directionToBeAdded", 0, int.class)))).verify();
    }

    @Test
    @DisplayName("Überschriebene Methode \"addToDirectionOfOffspring\" wurde korrekt implementiert.")
    public void addToDirectionOfOffspringImplementedCorrectly() throws IllegalAccessException {
        // TODO: verify implementation (see page 9 bottom paragraph)
        var addToDirectionOfOffspringMethod = new MethodTester(
            robotWithOffspring2CT.resolve(), "addToDirectionOfOffspring", 0.8,
            Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(new ParameterMatcher("directionToBeAdded", 0, int.class))))
            .resolveMethod();

        var directionAccuField = robotWithOffspring2CT.resolve().resolveAttribute(
            new AttributeMatcher("directionAccu", 0.8, int.class));
        assertFalse(directionAccuField.getType().isArray(),
            String.format("Der Datentyp von Attribut \"%s\" ist ein Array, sollte aber kein Array sein.",
                directionAccuField.getName()));
        directionAccuField.setAccessible(true);

        var robotWithOffspring2Instance = robotWithOffspring2CT.getClassInstance();

        // Verify that value of directionAccuField does not change if offspring has not been initialized
        final int value = 4738;
        directionAccuField.set(robotWithOffspring2Instance, value);
        assertDoesNotThrow(() -> addToDirectionOfOffspringMethod.invoke(robotWithOffspring2Instance, 193),
            String.format("Die Methode \"%s\" wirft eine unerwartete Exception, wenn \"offspring\" nicht initialisiert wurde.",
                addToDirectionOfOffspringMethod.getName()));
        assertEquals(value, directionAccuField.get(robotWithOffspring2Instance),
            String.format("Die Methode \"%s\" ändert den Wert von \"%s\", wenn \"offspring\" nicht initialisiert wurde.",
                addToDirectionOfOffspringMethod.getName(), directionAccuField.getName()));

        // Initialize offspring


        // TODO: Verify that directionAccuField is updated

//        var directionAccuFieldBefore = directionAccuField.get(robotWithOffspring2Instance);
//
//        assertDoesNotThrow(() -> initOffspringMethod.invoke(robotWithOffspring2Instance, direction, 42),
//            "Die Methode \"initOffspring\" wurde nicht korrekt deklariert.");
//        assertEquals(expectedResultValue, directionAccuField.get(robotWithOffspring2Instance),
//            String.format("Die Methode \"initOffspring\" setzt das Attribut \"directionAccu\" nicht korrekt für Direction %s.",
//                direction));
    }
}
