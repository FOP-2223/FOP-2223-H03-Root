package h03.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Function;

import static org.objectweb.asm.Opcodes.*;

public class H1_3_Transformers {

    public static boolean floorModInsnPresent;

    public static final Function<ClassWriter, ClassVisitor> FLOOR_MOD_CHECK_TRANSFORMER = classWriter ->
        new ClassVisitor(ASM9, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if ("addToDirectionOfOffspring".equals(name) && "(I)V".equals(descriptor)) {
                    return new MethodVisitor(ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (opcode == Opcodes.INVOKESTATIC
                                && "java/lang/Math".equals(owner)
                                && "floorMod".equals(name)) {
                                floorModInsnPresent = true;
                            }
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    };
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        };
}
