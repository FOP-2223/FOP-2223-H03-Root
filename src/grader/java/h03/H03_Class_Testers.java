package h03;

import fopbot.Robot;
import org.tudalgo.algoutils.reflect.ClassTester;

import java.lang.reflect.Modifier;

public class H03_Class_Testers {
    public final static double minSim = 0.8d;
    public final static ClassTester<?> robotWithOffspringCT = new ClassTester<>("h03", "RobotWithOffspring",
        minSim, Modifier.PUBLIC,
        Robot.class, null);

    public final static ClassTester<?> robotWithOffspring2CT = new ClassTester<>("h03", "RobotWithOffspring2",
        minSim, Modifier.PUBLIC,
        robotWithOffspringCT.getTheClass(), null);

    public final static ClassTester<?> twinRobotsCT = new ClassTester<>("h03", "TwinRobots",
        minSim, Modifier.PUBLIC, null, null);
}
