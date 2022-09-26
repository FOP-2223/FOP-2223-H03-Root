package h03;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h03.ReflectionUtils.AttributeMatcher;
import h03.ReflectionUtils.ClassTester;
import h03.ReflectionUtils.MethodTester;
import h03.ReflectionUtils.ParameterMatcher;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h03")
@DisplayName("H1.3")
public class TutorTests_H1_3 {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
        World.setSize(500, 500);
    }

    @Test
    @DisplayName("1 | Methode addToXOfOffspring")
    // TODO: Parametrisierter Test
    // TODO: test multiple cases for too big, too small etc.
    public void t01() {
        var offspring = new Robot(12, 32, Direction.UP, 34);
        testAddToMethod("addToXOfOffspring", FieldEntity::getX, offspring, 44, 56, true);
    }

    @Test
    @DisplayName("2 | Methode addToYOfOffspring")
    // TODO: Parametrisierter Test
    // TODO: test multiple cases for too big, too small etc.
    public void t02() {
        var offspring = new Robot(12, 32, Direction.UP, 34);
        testAddToMethod("addToYOfOffspring", FieldEntity::getY, offspring, 44, 76, true);
    }

    @Test
    @DisplayName("3 | Methode addToDirectionOfOffspring")
    // TODO: Parametrisierter Test
    // TODO: test multiple cases for too big, too small etc.
    public void t03() {
        var offspring = new Robot(12, 32, Direction.UP, 34);
        testAddToMethod("addToDirectionOfOffspring", Robot::getDirection, offspring, 43, Direction.LEFT, false);
    }

    @Test
    @DisplayName("4 | Methode addToNumberOfCoinsOfOffspring")
    // TODO: Parametrisierter Test
    // TODO: test multiple cases for too big, too small etc.
    public void t04() {
        var offspring = new Robot(12, 32, Direction.UP, 34);
        testAddToMethod("addToNumberOfCoinsOfOffspring", Robot::getNumberOfCoins, offspring, 23, 57, false);
        // TODO: check no library is used for modular arithmetic
    }

    private interface ValueGetter<T> {
        T getValue(Robot robot);
    }

    private <T> void testAddToMethod(String methodName, @NotNull ValueGetter<T> valueGetter, Robot offspring,
                                     int valueToAssign, T expectedResultValue, boolean setWorldSize) {
        ClassTester<?> ct = robotWithOffspringCT.resolve();
        Field offspringField = ct.resolveAttribute(
            new AttributeMatcher("offspring", 0.8, Modifier.PROTECTED,
                Robot.class));

        Object robotInstance = robotWithOffspringCT.getClassInstance();

        if (setWorldSize) {
            Field numberOfColumnsOfWorldField = ct.resolveAttribute(
                new AttributeMatcher("numberOfColumnsOfWorld", 0.8, Modifier.PRIVATE, int.class));
            assertDoesNotThrow(() -> numberOfColumnsOfWorldField.setAccessible(true));
            assertDoesNotThrow(() -> numberOfColumnsOfWorldField.set(robotInstance, World.getWidth()));

            Field numberOfRowsOfWorldField = ct.resolveAttribute(
                new AttributeMatcher("numberOfRowsOfWorld", 0.8, Modifier.PRIVATE, int.class));
            assertDoesNotThrow(() -> numberOfRowsOfWorldField.setAccessible(true));
            assertDoesNotThrow(() -> numberOfRowsOfWorldField.set(robotInstance, World.getHeight()));
        }

        var methodTester = new MethodTester(
            ct,
            methodName,
            0.8,
            Modifier.PUBLIC,
            void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("foobar", 0, int.class)
            ))).verify();

        assertDoesNotThrow(() -> methodTester.invoke(13),
            String.format("Die Methode %s wirft eine Exception wenn offspring nicht initialisiert ist.",
                methodName));

        assertDoesNotThrow(() -> offspringField.set(robotInstance, offspring));
        assertDoesNotThrow(() -> methodTester.invoke(valueToAssign));
        var newValue = valueGetter.getValue(offspring);
        assertEquals(expectedResultValue, newValue,
            String.format("Die Methode %s weist dem offspring den falschen Attributwert zu.", methodName));
    }
}
