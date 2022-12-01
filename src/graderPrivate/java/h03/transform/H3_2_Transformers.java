package h03.transform;

import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.objectweb.asm.Opcodes.*;

public class H3_2_Transformers {

    public static int twinRobotsMethodInvocations;
    public static int assertionsInvocations;
    public static boolean withNegativeArgument;
    public static List<Object[]> arguments = new ArrayList<>();
    public static String addToDirectionOfBothOffspringsMethodName;

    public static final Function<ClassWriter, ClassVisitor> UNIT_TEST_TRANSFORMER = classWriter ->
        new ClassVisitor(ASM9, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals("testRobotWithOffspringTwins") && descriptor.equals("()V")) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (opcode == Opcodes.INVOKEVIRTUAL
                                && owner.equals("h03/TwinRobots")
                                && name.equals(addToDirectionOfBothOffspringsMethodName)
                                && descriptor.equals("(I)V")) {
                                ParameterInterceptor interceptor = new ParameterInterceptor(this);
                                interceptor.interceptParameters(Type.getArgumentTypes(descriptor));
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/transform/H3_2_Transformers",
                                    "arguments",
                                    "Ljava/util/List;");
                                super.visitInsn(Opcodes.SWAP);
                                super.visitMethodInsn(Opcodes.INVOKEINTERFACE,
                                    "java/util/List",
                                    "add",
                                    "(Ljava/lang/Object;)Z",
                                    true);
                                super.visitInsn(Opcodes.POP);
                                super.visitInsn(Opcodes.DUP);
                                super.visitInsn(Opcodes.ICONST_M1);
                                super.visitInsn(Opcodes.IAND);
                                super.visitIntInsn(Opcodes.BIPUSH, 31);
                                super.visitInsn(Opcodes.ISHR);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/transform/H3_2_Transformers",
                                    "withNegativeArgument",
                                    "Z");
                                super.visitInsn(Opcodes.IOR);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h03/transform/H3_2_Transformers",
                                    "withNegativeArgument",
                                    "Z");
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/transform/H3_2_Transformers",
                                    "twinRobotsMethodInvocations",
                                    "I");
                                super.visitInsn(Opcodes.ICONST_1);
                                super.visitInsn(Opcodes.IADD);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h03/transform/H3_2_Transformers",
                                    "twinRobotsMethodInvocations",
                                    "I");
                            } else if (opcode == Opcodes.INVOKESTATIC
                                && owner.equals("org/junit/jupiter/api/Assertions")) {
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                    "h03/transform/H3_2_Transformers",
                                    "assertionsInvocations",
                                    "I");
                                super.visitInsn(Opcodes.ICONST_1);
                                super.visitInsn(Opcodes.IADD);
                                super.visitFieldInsn(Opcodes.PUTSTATIC,
                                    "h03/transform/H3_2_Transformers",
                                    "assertionsInvocations",
                                    "I");
                            } else {
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                            }
                        }
                    };
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        };
}
