package h03.transform;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

import static org.junit.jupiter.api.Assertions.fail;

public class FloorModCheckTransformer implements ClassTransformer {
    @Override
    public String getName() {
        return "FloorModCheck-transformer";
    }

    @Override
    public void transform(@NotNull final ClassReader reader, final ClassWriter writer) {
        reader.accept(new CV(), 0);
    }

    private static class CV extends ClassVisitor {
        public CV() {
            super(Opcodes.ASM9);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor,
                                         final String signature, final String[] exceptions) {
            if ("addToDirectionOfOffspring".equals(name) && "(I)V".equals(descriptor)) {
                return new MV();
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        private static class MV extends MethodVisitor {
            public MV() {
                super(Opcodes.ASM9);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                if ("org/sourcegrade/jagr/core/executor/TimeoutHandler".equals(owner) && "checkTimeout".equals(name)) {
                    return;
                }

                if (opcode == Opcodes.INVOKESTATIC && "java/lang/Math".equals(owner)
                    && "floorMod".equals(name) && "(II)I".equals(descriptor)) {
                    fail("Es wurde die verbotene Funktion \"Math.floorMod\" für modulare Arithmetik verwendet.");
                }
            }
        }
    }
}
