package h03.transform;

import org.junit.jupiter.api.Assertions;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class RobotWithOffspring2TransformerForGetDirectionOfOffspring implements ClassTransformer {
    private final String getDirectionOfOffspringMethodName;
    private final String getDirectionFromAccuMethodName;

    public RobotWithOffspring2TransformerForGetDirectionOfOffspring(String getDirectionOfOffspringMethodName,
                                                                    String getDirectionFromAccuMethodName) {
        this.getDirectionOfOffspringMethodName = getDirectionOfOffspringMethodName;
        this.getDirectionFromAccuMethodName = getDirectionFromAccuMethodName;
    }

    @Override
    public String getName() {
        return "RobotWithOffspring2-transformerForGetDirectionOfOffspring";
    }

    @Override
    public void transform(final ClassReader reader, final ClassWriter writer) {
        reader.accept(new CV(getDirectionOfOffspringMethodName, getDirectionFromAccuMethodName), 0);
    }

    private static class CV extends ClassVisitor {
        private final String getDirectionOfOffspringMethodName;
        private final String getDirectionFromAccuMethodName;

        public CV(String getDirectionOfOffspringMethodName, String getDirectionFromAccuMethodName) {
            super(Opcodes.ASM9);
            this.getDirectionOfOffspringMethodName = getDirectionOfOffspringMethodName;
            this.getDirectionFromAccuMethodName = getDirectionFromAccuMethodName;
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor,
                                         final String signature, final String[] exceptions) {
            if (getDirectionOfOffspringMethodName.equals(name) && "()Lfopbot/Direction;".equals(descriptor)) {
                return new MV(getDirectionFromAccuMethodName);
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        private static class MV extends MethodVisitor {
            private final String getDirectionFromAccuMethodName;
            final int[] expectedOpCodes = {Opcodes.ALOAD};
            int currentIndex = 0;

            public MV(String getDirectionFromAccuMethodName) {
                super(Opcodes.ASM9);
                this.getDirectionFromAccuMethodName = getDirectionFromAccuMethodName;
            }

            @Override
            public void visitInsn(int opcode) {
                Assertions.assertEquals(2, currentIndex);
                Assertions.assertEquals(Opcodes.ARETURN, opcode);
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

                Assertions.assertEquals(1, currentIndex);
                Assertions.assertEquals(Opcodes.INVOKEVIRTUAL, opcode);
                Assertions.assertEquals("h03/RobotWithOffspring2", owner);
                Assertions.assertEquals(getDirectionFromAccuMethodName, name);
                Assertions.assertEquals("()Lfopbot/Direction;", descriptor);
                currentIndex++;
            }
        }
    }
}
