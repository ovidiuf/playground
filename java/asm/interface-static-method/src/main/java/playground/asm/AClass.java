package playground.asm;

public class AClass {

    public AClass() {
    }

    public void exec() {
        String var1 = AnInterface.aStaticInterfaceMethod("test");
        System.out.println(var1);
    }
}
