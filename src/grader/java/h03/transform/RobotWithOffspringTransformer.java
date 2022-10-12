package h03.transform;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.launcher.env.Jagr;

import java.util.Arrays;

public class RobotWithOffspringTransformer implements ClassTransformer {
    @Override
    public String getName() {
        return "RobotWithOffspring-transformer";
    }

    @Override
    public void transform(final ClassReader reader, final ClassWriter writer) {
        reader.accept(new CV(writer), 0);
    }

    private static class CV extends ClassVisitor {
        public CV(ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            Jagr.Default.getInjector().getInstance(Logger.class).warn(String.format("%s, %s, %s, %s, %s, %s", version, access,
                name, signature, superName, Arrays.toString(interfaces)));
            super.visit(version, access, name, signature, "h03/TutorRobot", interfaces);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor,
                                         final String signature, final String[] exceptions) {
            return new MV(super.visitMethod(access, name, descriptor, signature, exceptions));
        }

        private static class MV extends MethodVisitor {
            public MV(MethodVisitor visitor) {
                super(Opcodes.ASM9, visitor);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if ("fopbot/Robot".equals(owner) && Opcodes.INVOKESPECIAL == opcode) {
                    super.visitMethodInsn(opcode, "h03/TutorRobot", name, descriptor, isInterface);
                } else {
                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }
}
