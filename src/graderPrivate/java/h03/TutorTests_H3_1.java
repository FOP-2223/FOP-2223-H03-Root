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
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.assertions.TestOfObject;
import org.tudalgo.algoutils.tutor.general.assertions.expected.ExpectedObject;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static h03.H03_Class_Testers.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
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
    public void testRobotsField() {
        robotsField = twinRobotsCT.resolve().resolveAttribute(
            new AttributeMatcher("robots", MIN_SIM, Modifier.PRIVATE, robotWithOffspringCT.assureClassResolved().getClass()));

        assertTrue(robotsField.getType().isArray(), emptyContext(), result -> "expected field robots to be an array but it isn't");
        robotsField.trySetAccessible();
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({"12,32"})
    @DisplayName("2 | Konstruktor")
    @ExtendWith(TestCycleResolver.class)
    @SuppressWarnings("unchecked")
    public void testTwinRobotsConstructor(int numberOfColumnsOfWorld, int numberOfRowsOfWorld, TestCycle testCycle)
        throws ReflectiveOperationException {
        if (robotsField == null) {
            fail(emptyContext(), result -> "Field robots could not be resolved");
        }

        ParameterMatcher numberOfColumnsOfWorldParameterMatcher = new ParameterMatcher("numberOfColumnsOfWorld", MIN_SIM_PARAM, int.class);
        ParameterMatcher numberOfRowsOfWorldParameterMatcher = new ParameterMatcher("numberOfRowsOfWorld", MIN_SIM_PARAM, int.class);
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
        Constructor<?> twinRobotsConstructor = clazz.getDeclaredConstructor(int.class, int.class);
        Object transformedInstance = twinRobotsConstructor.newInstance(numberOfColumnsOfWorld, numberOfRowsOfWorld);

        List<Object[]> robotWithOffspringInvocations = (List<Object[]>) H3_1_Transformers.CONSTRUCTOR_VALUES.get("robotWithOffspringParams");
        List<Object[]> robotWithOffspring2Invocations = (List<Object[]>) H3_1_Transformers.CONSTRUCTOR_VALUES.get("robotWithOffspring2Params");

        assertEquals(1, robotWithOffspringInvocations.size(), context,
            result -> "constructor of RobotWithOffspring was not invoked exactly once");
        assertEquals(1, robotWithOffspring2Invocations.size(), context,
            result -> "constructor of RobotWithOffspring2 was not invoked exactly once");

        Object[] expectedParams0 = {numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.RIGHT, 0};
        Object[] expectedParams1 = {numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.UP, 0};
        TestOfObject<Object[]> paramTesterRobotWithOffspring = Assertions2.<Object[]>testOfObjectBuilder()
            .expected(ExpectedObject.of(expectedParams0, actual -> Arrays.equals(expectedParams0, actual)))
            .build();
        TestOfObject<Object[]> paramTesterRobotWithOffspring2 = Assertions2.<Object[]>testOfObjectBuilder()
            .expected(ExpectedObject.of(expectedParams1, actual -> Arrays.equals(expectedParams1, actual)))
            .build();

        paramTesterRobotWithOffspring.run(robotWithOffspringInvocations.get(0))
            .check(context, result -> "constructor of RobotWithOffspring was not invoked with the expected arguments");
        paramTesterRobotWithOffspring2.run(robotWithOffspring2Invocations.get(0))
            .check(context, result -> "constructor of RobotWithOffspring2 was not invoked with the expected arguments");
    }

    @Test
    @Order(3)
    @DisplayName("Methode \"getRobotByIndex\" wurde korrekt deklariert.")
    public void testGetRobotByIndex() throws ReflectiveOperationException {
        MethodTester methodTester = new MethodTester(twinRobotsCT.resolve(), "getRobotByIndex", MIN_SIM, Modifier.PUBLIC,
            robotWithOffspringCT.assureClassResolved().getTheClass(),
            new ArrayList<>(List.of(new ParameterMatcher("index", MIN_SIM_PARAM, int.class)))).verify();

        Object twinRobotsInstance = twinRobotsCT.resolve().getClassInstance();
        var array = new RobotWithOffspring[2];
        array[0] = new RobotWithOffspring(12, 33, Direction.LEFT, 68);
        array[1] = new RobotWithOffspring2(53, 76, Direction.RIGHT, 361);
        robotsField.set(twinRobotsInstance, array);

        Context context = contextBuilder()
            .add("robots", Arrays.toString(array))
            .build();

        assertCallEquals(array[0], () -> methodTester.invoke(0),
            contextBuilder().add(context).add("index", 0).build(),
            result -> "Method getRobotByIndex(int) did not return the expected object");
        assertCallEquals(array[1], () -> methodTester.invoke(1),
            contextBuilder().add(context).add("index", 1).build(),
            result -> "Method getRobotByIndex(int) did not return the expected object");
    }

    @Test
    @Order(4)
    @DisplayName("Methode \"addToDirectionOfBothOffsprings\" wurde korrekt deklariert.")
    @ExtendWith(TestCycleResolver.class)
    public void testAddToDirectionOfBothOffsprings(TestCycle testCycle) throws ReflectiveOperationException {
        MethodTester methodTester = new MethodTester(twinRobotsCT.resolve(),
            "addToDirectionOfBothOffsprings",
            MIN_SIM,
            Modifier.PUBLIC,
            void.class,
            new ArrayList<>(List.of(new ParameterMatcher("directionToBeAdded", MIN_SIM_PARAM, int.class)))).verify();
        Class<?> twinRobotsClass = testCycle.getClassLoader().loadClass(twinRobotsCT.findClass().getName(),
            new ClassTransformerTemplate(twinRobotsCT.getTheClass().getName(), H3_1_Transformers.ADD_TO_DIRECTION_OF_OFFSPRING_TRANSFORMER));
        Field robotsField = twinRobotsClass.getDeclaredField("robots");
        Object instance = twinRobotsClass.getDeclaredConstructor(int.class, int.class).newInstance(0, 0);
        H3_1_Transformers.addToDirectionOfOffspringInvocations = 0;

        robotsField.trySetAccessible();
        twinRobotsClass.getDeclaredMethod("addToDirectionOfBothOffsprings", int.class).invoke(instance, 0);

        Context context = contextBuilder()
            .add("robots", Arrays.toString((Object[]) robotsField.get(instance)))
            .add("directionToBeAdded", 0)
            .build();
        testOfObjectBuilder()
            .expected(ExpectedObject.of("at least 2 times", o -> o instanceof Integer i && i >= 2))
            .build()
            .run(H3_1_Transformers.addToDirectionOfOffspringInvocations)
            .check(context, result -> "The robot's addDirectionToOffspring(int) methods have not been invoked at least once each");
    }
}
