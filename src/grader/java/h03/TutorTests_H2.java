package h03;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h03.ArgumentsProviders.WorldArgumentsProvider;
import h03.ReflectionUtils.AttributeMatcher;
import h03.ReflectionUtils.ClassTester;
import h03.ReflectionUtils.MethodTester;
import h03.ReflectionUtils.ParameterMatcher;
import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.internal.matchers.Not;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h03.H03_Class_Testers.robotWithOffspring2CT;
import static h03.H03_Class_Testers.robotWithOffspringCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h03")
@DisplayName("H2")
public class TutorTests_H2 {
    final String class_name = "RobotWithOffspring2";

    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
        World.setSize(500, 500);
    }

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    @SuppressWarnings("unchecked")
    public void t01() {
        robotWithOffspring2CT.setSuperClass((Class<Object>) robotWithOffspringCT.assureClassResolved().getTheClass());
        robotWithOffspring2CT.verify(1);
    }

    @Test
    @DisplayName("2 | Attribut directionAccu")
    public void t02() {
        robotWithOffspring2CT.resolve().resolveAttribute(
            new AttributeMatcher("directionAccu", 1, Modifier.PRIVATE,
                int.class));
        // TODO: final keyword zulassen? derzeit verboten und Punktabzug, wenn nicht explizit gefordert
    }

    @Test
    @DisplayName("3 | Konstruktor")
    public void t03() {
        // TODO: check that super constructor is called properly with class visitor
    }

    @Test
    @DisplayName("4 | Überschriebene Methode initOffspring")
    public void t04() {
        // TODO: check initOffspring
    }
}
