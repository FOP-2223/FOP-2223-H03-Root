package h03.transform;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.objectweb.asm.Opcodes.*;

public class ParameterInterceptor {

    private final MethodVisitor mv;

    public ParameterInterceptor(MethodVisitor mv) {
        this.mv = mv;
    }

    public void interceptParameters(Type[] types) {
        mv.visitIntInsn(BIPUSH, types.length);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
        for (int i = types.length - 1; i >= 0; i--) {
            mv.visitInsn(DUP_X1);
            mv.visitInsn(SWAP);
            mv.visitIntInsn(BIPUSH, i);
            mv.visitInsn(SWAP);
            boxPrimitiveValue(types[i]);
            mv.visitInsn(AASTORE);
        }
        for (int i = 0; i < types.length; i++) {
            mv.visitInsn(DUP);
            mv.visitIntInsn(BIPUSH, i);
            mv.visitInsn(AALOAD);
            unboxPrimitiveValue(types[i]);
            mv.visitInsn(SWAP);
        }
    }

    private void boxPrimitiveValue(Type type) {
        switch (type.getDescriptor()) {
            case "I" -> {
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            }
        }
    }

    private void unboxPrimitiveValue(Type type) {
        switch (type.getDescriptor()) {
            case "I" -> {
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            }
        }
    }

    public void storeArrayRef(Consumer<MethodVisitor> consumer) {
        consumer.accept(mv);
    }
}
