package h03;

import fopbot.Direction;
import fopbot.World;
import h03.transform.ClassTransformerTemplate;
import h03.transform.H3_1_Transformers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static h03.H03_Class_Testers.robotWithOffspring2CT;
import static h03.H03_Class_Testers.robotWithOffspringCT;
import static h03.H03_Class_Testers.twinRobotsCT;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

//@TestForSubmission
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TutorTests_H3_1 {

    private static Field robotsField;

    @BeforeAll
    public static void setup() {
        World.reset();
        World.setSize(500, 500);
        World.setDelay(0);
        World.setVisible(false);

        robotWithOffspringCT.resolve();
        robotWithOffspring2CT.resolve();
        twinRobotsCT.resolve();
    }

    // DONE
    @Test
    @Order(1)
    @DisplayName("Attribut \"robots\" wurde korrekt deklariert.")
    public void robotsDeclaredCorrectly() {
        robotsField = twinRobotsCT.resolve().resolveAttribute(
            new AttributeMatcher("robots", 1, Modifier.PRIVATE, robotWithOffspringCT.assureClassResolved().getClass()));

        assertTrue(robotsField.getType().isArray(), emptyContext(),
            result -> "expected field robots to be an array but it isn't");
        robotsField.trySetAccessible();
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({"12,32"})
    @DisplayName("2 | Konstruktor")
    @ExtendWith(TestCycleResolver.class)
    @SuppressWarnings("unchecked")
    public void t02(int numberOfColumnsOfWorld, int numberOfRowsOfWorld, TestCycle testCycle) throws ReflectiveOperationException {
        if (robotsField == null) {
            fail(emptyContext(), result -> "Field robots could not be resolved");
        }

        ParameterMatcher numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", 0.8, int.class);
        ParameterMatcher numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", 0.8, int.class);
        Constructor<Object> constructor = (Constructor<Object>) twinRobotsCT.resolveConstructor(
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher);

        ((ClassTester<Object>) twinRobotsCT).assertConstructorValid(constructor, Modifier.PUBLIC,
            numberOfColumnsOfWorldParameterMatcher, numberOfRowsOfWorldParameterMatcher);
        ((ClassTester<Object>) twinRobotsCT).setClassInstance(constructor.newInstance(numberOfColumnsOfWorld, numberOfRowsOfWorld));

        Object instance = twinRobotsCT.getClassInstance();
        Context context = contextBuilder()
            .add("numberOfColumnsOfWorld", numberOfColumnsOfWorld)
            .add("numberOfRowsOfWorld", numberOfRowsOfWorld)
            .build();

        assertEquals(2, Array.getLength(robotsField.get(instance)), context,
            result -> "array robots does not have expected length");

        Object firstRobot = Array.get(robotsField.get(instance), 0);
        Object secondRobot = Array.get(robotsField.get(instance), 1);

        assertEquals(robotWithOffspringCT.getTheClass(), firstRobot.getClass(), context,
            result -> "object at robots[0] is not of type RobotWithOffspring");
        assertEquals(robotWithOffspring2CT.getTheClass(), secondRobot.getClass(), context,
            result -> "object at robots[1] is not of type RobotWithOffspring2");

        H3_1_Transformers.CONSTRUCTOR_VALUES.clear();
        H3_1_Transformers.CONSTRUCTOR_VALUES.put("robotWithOffspringParams", new ArrayList<>());
        H3_1_Transformers.CONSTRUCTOR_VALUES.put("robotWithOffspring2Params", new ArrayList<>());

        String className = twinRobotsCT.getTheClass().getName();
        Class<?> clazz = testCycle.getClassLoader()
            .loadClass(className, new ClassTransformerTemplate(className, H3_1_Transformers.CONSTRUCTOR_TRANSFORMER));
        Constructor<?> twinRobotsConstructor = clazz.getDeclaredConstructor(int.class, int.class, Direction.class, int.class);
        Object transformedInstance = twinRobotsConstructor.newInstance(0, 0, Direction.UP, 0);
        System.out.println();

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
    }
        /*assertEquals(robotWithOffspring2CT.assureClassResolved().getTheClass(), secondRobot.getClass(), "Das Objekt "
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
    }*/
}
