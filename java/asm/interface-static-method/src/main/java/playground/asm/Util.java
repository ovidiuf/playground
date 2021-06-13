package playground.asm;

import java.io.FileOutputStream;

public class Util {

    /**
     * @param fqClassNameDF fully qualified class name in dot format.
     */
    public static void writeClassToFile(byte[] classBytecode, String fqClassNameDF, String targetDir) throws Exception {
        String filename = targetDir + "/" + fqClassNameDF.replaceAll(".*\\.", "") + ".class";
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(classBytecode);
            System.out.println("wrote " + filename);
        }
    }
    public static Class<?> loadClass(String fqClassNameDF, byte[] classBytecode)  {
        Class<?> c = new GeneratedClassClassLoader().defineClass(fqClassNameDF, classBytecode);
        System.out.println(c + " loaded");
        return c;
    }
}
