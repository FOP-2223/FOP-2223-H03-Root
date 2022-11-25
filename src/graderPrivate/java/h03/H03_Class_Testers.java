package h03;

import fopbot.Robot;
import org.tudalgo.algoutils.reflect.ClassTester;

import java.lang.reflect.Modifier;

public class H03_Class_Testers {
    public static final double MIN_SIM = 0.8;
    public static final double MIN_SIM_PARAM = 0;
    public static final ClassTester<?> robotWithOffspringCT = new ClassTester<>("h03", "RobotWithOffspring",
        MIN_SIM, Modifier.PUBLIC, Robot.class, null);

    public static final ClassTester<?> robotWithOffspring2CT = new ClassTester<>("h03", "RobotWithOffspring2",
        MIN_SIM, Modifier.PUBLIC, robotWithOffspringCT.getTheClass(), null);

    public static final ClassTester<?> twinRobotsCT = new ClassTester<>("h03", "TwinRobots",
        MIN_SIM, Modifier.PUBLIC, null, null);
}
