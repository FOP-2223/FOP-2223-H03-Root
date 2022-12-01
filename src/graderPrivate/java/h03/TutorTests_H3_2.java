package h03;

import fopbot.Direction;
import h03.transform.ClassTransformerTemplate;
import h03.transform.H3_2_Transformers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.MIN_SIM;
import static h03.H03_Class_Testers.MIN_SIM_PARAM;
import static h03.transform.H3_2_Transformers.UNIT_TEST_TRANSFORMER;
import static h03.transform.H3_2_Transformers.arguments;
import static h03.transform.H3_2_Transformers.assertionsInvocations;
import static h03.transform.H3_2_Transformers.twinRobotsMethodInvocations;
import static h03.transform.H3_2_Transformers.withNegativeArgument;

@TestForSubmission
@ExtendWith(TestCycleResolver.class)
public class TutorTests_H3_2 {

    private static boolean isSetUp = false;

    public TutorTests_H3_2(TestCycle testCycle) {
        if (!isSetUp) {
            setup(testCycle);
            isSetUp = true;
        }
    }

    public void setup(TestCycle testCycle) {
        if (isSetUp) {
            return;
        }

        twinRobotsMethodInvocations = 0;
        assertionsInvocations = -1;
        withNegativeArgument = false;
        arguments.clear();
        ClassTester<?> classTester = new ClassTester<>("h03", "TwinRobots", 0.8).resolveClass();
        classTester.assertClassResolved();

        try {
            var addToDirectionOfBothOffspringsMethod = new MethodTester(classTester, "addToDirectionOfBothOffsprings", MIN_SIM,
                Modifier.PUBLIC, void.class,
                new ArrayList<>(List.of(
                    new ParameterMatcher("directionToBeAdded", MIN_SIM_PARAM, int.class)
                ))).resolveMethod();

            H3_2_Transformers.addToDirectionOfBothOffspringsMethodName = addToDirectionOfBothOffspringsMethod.getName();
        } catch (Throwable ignored) {
            H3_2_Transformers.addToDirectionOfBothOffspringsMethodName = "addToDirectionOfBothOffsprings";
        }

        Class<?> h3_2_unitTestClass = testCycle.getClassLoader()
            .loadClass("h03.H3_2_UnitTest",
                new ClassTransformerTemplate(classTester.getClass().getName(), UNIT_TEST_TRANSFORMER));
        try {
            Object instance = h3_2_unitTestClass.getDeclaredConstructor().newInstance();
            h3_2_unitTestClass.getDeclaredMethod("testRobotWithOffspringTwins").invoke(instance);
        } catch (Throwable e) {
            throw new AssertionFailedError("Unexpected exception thrown", e);
        }
    }

    @Test
    public void testNumberOfInvocations() {
        Assertions2.assertTrue(twinRobotsMethodInvocations >= 3, Assertions2.emptyContext(),
            result -> "Expected addToDirectionOfBothOffsprings to be invoked at least 3 more times but only counted "
                + twinRobotsMethodInvocations);
        Assertions2.assertTrue(assertionsInvocations >= 3, Assertions2.emptyContext(),
            result -> "Expected any of JUnit's assertions methods to be invoked at least 3 more times but only counted "
                + assertionsInvocations);
    }

    @Test
    public void testNegativeArgument() {
        Assertions2.assertTrue(withNegativeArgument, Assertions2.emptyContext(),
            result -> "Expected TwinRobots.addToDirectionOfBothOffsprings to be called with negative a number at least once");
    }

    @Test
    public void testNegativeFieldValue() {
        int i = Direction.LEFT.ordinal();
        for (Object[] argumentArray : arguments) {
            if (i < 0) {
                return;
            }
            i += (Integer) argumentArray[0];
        }

        Assertions2.fail(Assertions2.emptyContext(),
            result -> "Expected TwinRobot.addToDirectionOfBothOffsprings to invoked at least once while the directionAccu field" +
                " of RobotWithOffspring2 is negative");
    }
}
