package h03.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.objectweb.asm.Opcodes.*;

public class H3_1_Transformers {

    public static final Map<String, Object> CONSTRUCTOR_VALUES = new HashMap<>();
    public static final Function<ClassWriter, ClassVisitor> CONSTRUCTOR_TRANSFORMER = classWriter -> new ClassVisitor(ASM9, classWriter) {
        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals("<init>") && descriptor.equals("(II)V")) {
                return new MethodVisitor(ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        ParameterInterceptor interceptor = new ParameterInterceptor(this);
                        if (opcode == INVOKESPECIAL
                            && owner.matches("h03/RobotWithOffspring2?")
                            && name.equals("<init>")
                            && descriptor.equals("(IILfopbot/Direction;I)V")) {
                            String key = owner.equals("h03/RobotWithOffspring")
                                ? "robotWithOffspringParams"
                                : "robotWithOffspring2Params";
                            interceptor.interceptParameters(Type.getArgumentTypes(descriptor));
                            super.visitFieldInsn(GETSTATIC,
                                "h03/transform/H3_1_Transformers",
                                "CONSTRUCTOR_VALUES",
                                "Ljava/util/Map;");
                            super.visitLdcInsn(key);
                            super.visitMethodInsn(INVOKEINTERFACE,
                                "java/util/Map",
                                "get",
                                "(Ljava/lang/Object;)Ljava/lang/Object;",
                                true);
                            super.visitTypeInsn(CHECKCAST, "java/util/List");
                            super.visitInsn(SWAP);
                            super.visitMethodInsn(INVOKEINTERFACE,
                                "java/util/List",
                                "add",
                                "(Ljava/lang/Object;)Z",
                                true);
                            super.visitInsn(POP);
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                };
            } else {
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }
    };

    public static int addToDirectionOfOffspringInvocations = 0;
    public static final Function<ClassWriter, ClassVisitor> ADD_TO_DIRECTION_OF_OFFSPRING_TRANSFORMER = writer ->
        new ClassVisitor(ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals("addToDirectionOfBothOffsprings") && descriptor.equals("(I)V")) {
                    return new MethodVisitor(ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (owner.startsWith("h03/RobotWithOffspring")
                                && name.equals("addToDirectionOfOffspring")
                                && descriptor.equals("(I)V")) {
                                visitFieldInsn(GETSTATIC,
                                    "h03/transform/H3_1_Transformers",
                                    "addToDirectionOfOffspringInvocations",
                                    "I");
                                visitInsn(ICONST_1);
                                visitInsn(IADD);
                                visitFieldInsn(PUTSTATIC,
                                    "h03/transform/H3_1_Transformers",
                                    "addToDirectionOfOffspringInvocations",
                                    "I");
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
