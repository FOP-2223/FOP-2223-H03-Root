package h03.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.objectweb.asm.Opcodes.*;

public class H2_Transformers {

    public static final Map<String, Object> CONSTRUCTOR_VALUES = new HashMap<>();
    public static final Function<ClassWriter, ClassVisitor> CONSTRUCTOR_TRANSFORMER = writer -> new ClassVisitor(ASM9, writer) {
        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals("<init>") && descriptor.equals("(IILfopbot/Direction;I)V")) {
                return new MethodVisitor(ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (opcode == INVOKESPECIAL
                            && owner.equals("h03/RobotWithOffspring")
                            && name.equals("<init>")
                            && descriptor.equals("(IILfopbot/Direction;I)V")) {
                            CONSTRUCTOR_VALUES.put("superConstructorCalled", true);
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        if (owner.equals("h03/RobotWithOffspring2") && name.equals("directionAccu")) {
                            CONSTRUCTOR_VALUES.put("fieldInstruction", true);
                        }
                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }
                };
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    };

    public static final Map<String, Object> INIT_OFFSPRING_VALUES = new HashMap<>();
    public static final Function<ClassWriter, ClassVisitor> INIT_OFFSPRING_TRANSFORMER = writer -> new ClassVisitor(ASM9, writer) {
        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals("initOffspring") && descriptor.equals("(Lfopbot/Direction;I)V")) {
                return new MethodVisitor(ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (opcode == INVOKESPECIAL
                            && owner.equals("h03/RobotWithOffspring")
                            && name.equals("initOffspring")
                            && descriptor.equals("(Lfopbot/Direction;I)V")) {
                            INIT_OFFSPRING_VALUES.put("invokedSuperMethod", true);
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                };
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    };
}
