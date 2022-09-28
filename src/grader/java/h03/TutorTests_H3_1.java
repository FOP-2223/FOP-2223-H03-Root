package h03;

import fopbot.World;
import h03.ReflectionUtils.AttributeMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Modifier;

import static h03.H03_Class_Testers.robotWithOffspringCT;
import static h03.H03_Class_Testers.twinRobotsCT;

@TestForSubmission("h03")
@DisplayName("H3.1")
public class TutorTests_H3_1 {
    @BeforeEach
    public void setup() {
        World.reset();
        World.setDelay(0);
        World.setVisible(false);
        World.setSize(500, 500);
    }

    @Test
    @DisplayName("1 | Attribut robots")
    public void t01() {
        twinRobotsCT.resolve().resolveAttribute(
            new AttributeMatcher("robots", 1, Modifier.PRIVATE,
                RobotWithOffspring[].class));
        // TODO: final keyword zulassen? derzeit verboten und Punktabzug, wenn nicht explizit gefordert
    }
}
