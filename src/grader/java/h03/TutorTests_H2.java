package h03;

import fopbot.Direction;
import fopbot.World;
import h03.ReflectionUtils.AttributeMatcher;
import h03.transform.RobotWithOffspring2Transformer;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.sourcegrade.jagr.launcher.env.Jagr;

import java.lang.reflect.Modifier;

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
    @ExtendWith(TestCycleResolver.class)
    public void t03(TestCycle testCycle) {
        // TODO: dynamically check name
        try {
            testCycle.getClassLoader().loadClass(RobotWithOffspring2.class.getName(),
                new RobotWithOffspring2Transformer());
            Jagr.Default.getInjector().getInstance(Logger.class).warn("Noerror");
        } catch (Throwable e) {
            Jagr.Default.getInjector().getInstance(Logger.class).warn("Error", e);
        }
    }

    @Test
    @DisplayName("4 | Überschriebene Methode initOffspring")
    public void t04() {
        // TODO: check initOffspring
    }
}
