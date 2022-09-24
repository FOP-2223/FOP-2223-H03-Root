package h03;

import fopbot.Robot;
import h03.ReflectionUtils.ClassTester;

import java.lang.reflect.Modifier;

public class H03_Class_Testers {
    public final static double minSim = 0.8d;
    public final static ClassTester<?> robotWithOffspringCT = new ClassTester<>("h03", "RobotWithOffspring",
        minSim, Modifier.PUBLIC,
        Robot.class, null);
}
