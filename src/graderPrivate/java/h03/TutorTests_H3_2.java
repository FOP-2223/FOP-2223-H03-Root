package h03;

import fopbot.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.objectweb.asm.*;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;

import java.util.ArrayList;
import java.util.List;

//@TestForSubmission
public class TutorTests_H3_2 {

    public static int twinRobotsMethodInvocations;
    public static int assertionsInvocations;
    public static boolean withNegativeArgument;
    public static List<Integer> arguments = new ArrayList<>();

    public static void setup(TestCycle testCycle) {
        twinRobotsMethodInvocations = 0;
        assertionsInvocations = -1;
        withNegativeArgument = false;
        arguments.clear();
        ClassTester<?> classTester = new ClassTester<>("h03", "TwinRobots", 0.8).resolveClass();
        classTester.assertClassResolved();

        Class<?> h3_2_unitTestClass = testCycle.getClassLoader().loadClass("h03.H3_2_UnitTest", new UnitTestTransformer());
        try {
            Object instance = h3_2_unitTestClass.getDeclaredConstructor().newInstance();
            h3_2_unitTestClass.getDeclaredMethod("testRobotWithOffspringTwins").invoke(instance);
        } catch (Throwable e) {
            throw new AssertionFailedError("Unexpected exception thrown", e);
        }
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    public void testNumberOfInvocations(TestCycle testCycle) {
        setup(testCycle);

        Assertions2.assertTrue(twinRobotsMethodInvocations >= 3, Assertions2.emptyContext(),
            result -> "Expected addToDirectionOfBothOffsprings to be invoked at least 3 more times but only counted "
                + twinRobotsMethodInvocations);
        Assertions2.assertTrue(assertionsInvocations >= 3, Assertions2.emptyContext(),
            result -> "Expected any of JUnit's assertions methods to be invoked at least 3 more times but only counted "
                + assertionsInvocations);
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    public void testNegativeArgument(TestCycle testCycle) {
        setup(testCycle);

        Assertions2.assertTrue(withNegativeArgument, Assertions2.emptyContext(),
            result -> "Expected TwinRobots.addToDirectionOfBothOffsprings to be called with negative a number at least once");
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    public void testNegativeFieldValue(TestCycle testCycle) {
        setup(testCycle);

        int i = Direction.LEFT.ordinal();
        for (int argument : arguments) {
            if (i < 0) {
                return;
            }
            i += argument;
        }

        Assertions2.fail(Assertions2.emptyContext(),
            result -> "Expected TwinRobot.addToDirectionOfBothOffsprings to invoked at least once while the directionAccu field" +
                " of RobotWithOffspring2 is negative");
    }

    private static class UnitTestTransformer implements ClassTransformer {
        @Override
        public String getName() {
            return "UnitTestTransformer";
        }

        @Override
        public int getWriterFlags() {
            return ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
        }

        @Override
        public void transform(ClassReader reader, ClassWriter writer) {
            if (reader.getClassName().equals("h03/H3_2_UnitTest")) {
                reader.accept(new CV(writer), ClassReader.SKIP_DEBUG | ClassReader.EXPAND_FRAMES);
            } else {
                reader.accept(writer, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            }
        }

        private static class CV extends ClassVisitor {
            protected CV(ClassVisitor classVisitor) {
                super(Opcodes.ASM9, classVisitor);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals("testRobotWithOffspringTwins") && descriptor.equals("()V")) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (opcode == Opcodes.INVOKEVIRTUAL
                                && owner.equals("h03/TwinRobots")
                                && name.equals("addToDirectionOfBothOffsprings")
                                && descriptor.equals("(I)V")) {
                                super.visitInsn(Opcodes.DUP);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/TutorTests_H3_2",
                                    "arguments",
                                    "Ljava/util/List;");
                                super.visitInsn(Opcodes.SWAP);
                                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                    "java/lang/Integer",
                                    "valueOf",
                                    "(I)Ljava/lang/Integer;",
                                    false);
                                super.visitMethodInsn(Opcodes.INVOKEINTERFACE,
                                    "java/util/List",
                                    "add",
                                    "(Ljava/lang/Object;)Z",
                                    true);
                                super.visitInsn(Opcodes.POP);
                                super.visitInsn(Opcodes.DUP);
                                super.visitInsn(Opcodes.ICONST_M1);
                                super.visitInsn(Opcodes.IAND);
                                super.visitIntInsn(Opcodes.BIPUSH, 31);
                                super.visitInsn(Opcodes.ISHR);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/TutorTests_H3_2",
                                    "withNegativeArgument",
                                    "Z");
                                super.visitInsn(Opcodes.IOR);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h03/TutorTests_H3_2",
                                    "withNegativeArgument",
                                    "Z");
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/TutorTests_H3_2",
                                    "twinRobotsMethodInvocations",
                                    "I");
                                super.visitInsn(Opcodes.ICONST_1);
                                super.visitInsn(Opcodes.IADD);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h03/TutorTests_H3_2",
                                    "twinRobotsMethodInvocations",
                                    "I");
                            } else if (opcode == Opcodes.INVOKESTATIC
                                && owner.equals("org/junit/jupiter/api/Assertions")) {
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/TutorTests_H3_2",
                                    "assertionsInvocations",
                                    "I");
                                super.visitInsn(Opcodes.ICONST_1);
                                super.visitInsn(Opcodes.IADD);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h03/TutorTests_H3_2",
                                    "assertionsInvocations",
                                    "I");
                            } else {
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                            }
                        }
                    };
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        }
    }
}
