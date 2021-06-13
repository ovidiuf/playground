package playground.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.PrintStream;

public class DynamicClassGeneration implements Opcodes {

    /**
     * @param fqClassNameDF fully qualified class name in dot format ("playground.asm.DClass").
     * @return the generated class bytecode
     */
    public static byte[] generate(String fqClassNameDF) {
        System.out.println("dynamically generating class " + fqClassNameDF);
        String fqClassNameIF = fqClassNameDF.replaceAll("\\.", "/");
        //
        // Use ClassWriter to dynamically build the class from bytecode
        //
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, fqClassNameIF, null, "java/lang/Object", null);
        //
        // generate default constructor
        //
        Method m = Method.getMethod("void <init> ()");
        GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);

        mg.loadThis();
        mg.invokeConstructor(Type.getType(Object.class), m);
        mg.returnValue();
        // mg.endMethod() which calls mv.visitMaxs(0, 0) ends up in java.lang.ClassFormatError
        // "Arguments can't fit into locals in class file playground/asm/DClass"
        // so calling those directly
        mg.visitMaxs(2, 1);
        mg.visitEnd();
        //
        // generate exec()
        //
        // public void exec() {
        //   String r = AnInterface.aStaticInterfaceMethod("test");
        //   System.out.println(r);
        // }
        //
        m = Method.getMethod("void exec ()");
        mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null, cw);
        mg.loadThis();
        int rVarIndex = mg.newLocal(Type.getType(String.class));
        // Call AnInterface.aStaticInterfaceMethod("test");
        // push the method argument to stack
        mg.push("test");
        // invoke the method
        Type targetType = Type.getType(AnInterface.class);
        Method targetMethod = Method.getMethod("String aStaticInterfaceMethod (String)");
        //mg.invokeStatic(owner, methodToInvoke);
        mg.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                targetType.getInternalName(),
                targetMethod.getName(),
                targetMethod.getDescriptor(),
                true);
        // store the result (top of the stack) in the local variable
        mg.storeLocal(rVarIndex);
        // invoke System.out.println()
        mg.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
        // mg.push("something");
        mg.loadLocal(rVarIndex);
        mg.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (String)"));
        mg.returnValue();
        // mg.endMethod() which calls mv.visitMaxs(0, 0) ends up in java.lang.ClassFormatError
        // "Arguments can't fit into locals in class file playground/asm/DClass"
        // so calling those directly
        mg.visitMaxs(3, 2);
        mg.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }
}
