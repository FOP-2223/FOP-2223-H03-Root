package h03.transform;

import org.junit.jupiter.api.Assertions;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class RobotWithOffspring2TransformerForInitOffspring implements ClassTransformer {
    private final String initOffspringMethodName;

    public RobotWithOffspring2TransformerForInitOffspring(String initOffspringMethodName) {
        this.initOffspringMethodName = initOffspringMethodName;
    }

    @Override
    public String getName() {
        return "RobotWithOffspring2-transformerForInitOffspring";
    }

    @Override
    public void transform(final ClassReader reader, final ClassWriter writer) {
        reader.accept(new CV(initOffspringMethodName), 0);
    }

    private static class CV extends ClassVisitor {
        private final String initOffspringMethodName;

        public CV(String initOffspringMethodName) {
            super(Opcodes.ASM9);
            this.initOffspringMethodName = initOffspringMethodName;
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor,
                                         final String signature, final String[] exceptions) {
            if (initOffspringMethodName.equals(name) && "(Lfopbot/Direction;I)V".equals(descriptor)) {
                return new MV(initOffspringMethodName);
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        private static class MV extends MethodVisitor {
            private final String initOffspringMethodName;
            final int[] expectedOpCodes = {Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD};
            int currentIndex = 0;

            public MV(String initOffspringMethodName) {
                super(Opcodes.ASM9);
                this.initOffspringMethodName = initOffspringMethodName;
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

                Assertions.assertEquals(3, currentIndex);
                Assertions.assertEquals(Opcodes.INVOKESPECIAL, opcode);
                Assertions.assertEquals("h03/RobotWithOffspring", owner);
                Assertions.assertEquals(initOffspringMethodName, name);
                Assertions.assertEquals("(Lfopbot/Direction;I)V", descriptor);
            }
        }
    }
}
