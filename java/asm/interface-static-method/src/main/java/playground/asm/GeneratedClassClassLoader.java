package playground.asm;

public class GeneratedClassClassLoader extends ClassLoader {

    /**
     * @param fqClassNameDF fully qualified class name in "dot" format.
     */
    Class<?> defineClass(String fqClassNameDF, byte[] byteCode) {
        return defineClass(fqClassNameDF, byteCode, 0, byteCode.length);
    }
}
