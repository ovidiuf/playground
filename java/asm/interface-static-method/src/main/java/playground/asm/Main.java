package playground.asm;

import java.lang.reflect.Method;

public class Main {

    private static final String DEFAULT_TARGET_DIR = "/Users/ovidiu/tmp";

    public static void main(String[] args) throws Exception {
        //
        // Dynamic class generation
        //
        String fqClassNameDF = "playground.asm.DClass";
        byte[] classBytecode = DynamicClassGeneration.generate(fqClassNameDF);
        Util.writeClassToFile(classBytecode, fqClassNameDF, DEFAULT_TARGET_DIR);
        Class<?> dClass = Util.loadClass(fqClassNameDF, classBytecode);
        Object d = dClass.newInstance();
        System.out.println("instance of " + dClass + ": " + d);
        Method execMethod = dClass.getMethod("exec");
        execMethod.invoke(d);

        //
        // Equivalent compiled class
        //
        AClass c = new AClass();
        c.exec();
    }
}
