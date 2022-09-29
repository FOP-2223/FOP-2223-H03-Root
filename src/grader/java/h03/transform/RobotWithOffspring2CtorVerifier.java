package h03.transform;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class RobotWithOffspring2CtorVerifier implements ClassTransformer {
    @Override
    public String getName() {
        return "RobotWithOffspring2Ctor-transformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if ("h03/RobotWithOffspring2".equals(reader.getClassName())) {
            reader.accept(new CV(Opcodes.ASM9, writer), 0);
        } else {
            reader.accept(writer, 0);
        }
    }

    private static class CV extends ClassVisitor {
        public CV(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if ("<init>".equals(name)
                && "(Ljava/lang/String;Ljava/lang/String;I)V".equals(descriptor)) {
                return new MV(api, super.visitMethod(access, name, descriptor, signature, exceptions));
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        private static class MV extends MethodVisitor {

            private final List<String> invokes = new ArrayList<>();

            public MV(int api, MethodVisitor methodVisitor) {
                super(api, methodVisitor);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                invokes.add(String.format("%s %s.%s%s", opcode, owner, name, descriptor));
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.RETURN) {
                    visitInlineArray();
                }
                super.visitInsn(opcode);
            }

            private void visitInlineArray() {
                visitInt(invokes.size());
                visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/String");
                ListIterator<String> it = invokes.listIterator();
                while (it.hasNext()) {
                    visitArrayElement(it.nextIndex(), it.next());
                }
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "h12/h1_1/StudentExamEntryMeta",
                    "setCtor3Invokes",
                    "([Ljava/lang/String;)V",
                    false
                );
            }

            private void visitArrayElement(int index, String value) {
                visitInsn(Opcodes.DUP);
                visitInt(index);
                visitLdcInsn(value);
                visitInsn(Opcodes.AASTORE);
            }

            private void visitInt(int value) {
                if (value >= 0 && value <= 5) {
                    visitInsn(Opcodes.ICONST_0 + value);
                } else {
                    visitIntInsn(Opcodes.BIPUSH, value);
                }
            }
        }
    }
}
