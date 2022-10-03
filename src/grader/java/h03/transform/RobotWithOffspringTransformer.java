package h03.transform;

import org.junit.jupiter.api.Assertions;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class RobotWithOffspringTransformer implements ClassTransformer {
    @Override
    public String getName() {
        return "RobotWithOffspring-transformer";
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

        private static class MV extends MethodVisitor {
            final int[][] expectedOpCodeVarIndex = {
                {Opcodes.ALOAD, 0},
                {Opcodes.ILOAD, 1},
                {Opcodes.ICONST_2},
                {Opcodes.IDIV},
                {Opcodes.ILOAD, 2},
                {Opcodes.ICONST_2},
                {Opcodes.IDIV},
                {Opcodes.ALOAD, 3},
                {Opcodes.ILOAD, 4},
            };
            int currentStatementIndex = 0;

            public MV() {
                super(Opcodes.ASM9);
            }

            @Override
            public void visitInsn(int opcode) {
                Assertions.assertEquals(expectedOpCodeVarIndex[currentStatementIndex][0], opcode);
                currentStatementIndex++;
            }

            @Override
            public void visitVarInsn(int opcode, int varIndex) {
                Assertions.assertEquals(expectedOpCodeVarIndex[currentStatementIndex][0], opcode);
                Assertions.assertEquals(expectedOpCodeVarIndex[currentStatementIndex][1], varIndex);
                currentStatementIndex++;
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if ("org/sourcegrade/jagr/core/executor/TimeoutHandler".equals(owner) && "checkTimeout".equals(name)) {
                    return;
                }

                Assertions.assertEquals(expectedOpCodeVarIndex.length, currentStatementIndex);
                Assertions.assertEquals(Opcodes.INVOKESPECIAL, opcode);
                Assertions.assertEquals("fopbot/Robot", owner);
                Assertions.assertEquals("<init>", name);
                Assertions.assertEquals("(IILfopbot/Direction;I)V", descriptor);
            }
        }
    }
}
