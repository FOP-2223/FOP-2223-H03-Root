package h03.transform;

import org.junit.jupiter.api.Assertions;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class RobotTransformer implements ClassTransformer {
    @Override
    public String getName() {
        return "Robot-transformer";
    }

    @Override
    public void transform(final ClassReader reader, final ClassWriter writer) {
        reader.accept(new CV(), 0);
    }

    private static class CV extends ClassVisitor {
        public CV() {
            super(Opcodes.ASM9);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor,
                                         final String signature, final String[] exceptions) {
            if ("<init>".equals(name) && "(IILfopbot/Direction;I)V".equals(descriptor)) {
                return new MV();
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        private class MV extends MethodVisitor {
            final int[] expectedOpCodes = {Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD, Opcodes.ALOAD, Opcodes.ILOAD};

            int currentIndex = 0;

            public MV() {
                super(Opcodes.ASM9);
            }

            @Override
            public void visitVarInsn(int opcode, int varIndex) {
                Assertions.assertEquals(expectedOpCodes[currentIndex], opcode);
                Assertions.assertEquals(currentIndex, varIndex);
                currentIndex++;
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if ("org/sourcegrade/jagr/core/executor/TimeoutHandler".equals(owner) && "checkTimeout".equals(name)) {
                    return;
                }

                Assertions.assertEquals(5, currentIndex);
                Assertions.assertEquals(Opcodes.INVOKESPECIAL, opcode);
                // TODO: dynamically check name
                Assertions.assertEquals("h03/RobotWithOffspring", owner);
                Assertions.assertEquals("<init>", name);
                Assertions.assertEquals("(IILfopbot/Direction;I)V", descriptor);
            }
        }
    }
}
