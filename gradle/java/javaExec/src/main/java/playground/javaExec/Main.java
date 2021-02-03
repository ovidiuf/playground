package playground.javaExec;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("no arguments");
        }
        else {
            System.out.println("arguments:");
            for(int i = 0; i < args.length; i ++) {
                System.out.println("  args[" + i + "]: " + args[i]);
            }
        }

        String s = System.getenv("SOME_ENV_VAR");
        if (s == null) {
            System.out.println("no environment variable: SOME_ENV_VAR");
        }
        else {
            System.out.println("SOME_ENV_VAR=" + s);
        }
        s = System.getenv("SOME_OTHER_ENV_VAR");
        if (s == null) {
            System.out.println("no environment variable: SOME_OTHER_ENV_VAR");
        }
        else {
            System.out.println("SOME_OTHER_ENV_VAR=" + s);
        }

        s = System.getProperty("some.prop");
        if (s == null) {
            System.out.println("no property: some.prop");
        }
        else {
            System.out.println("some.prop=" + s);
        }
        s = System.getProperty("some.other.prop");
        if (s == null) {
            System.out.println("no property: some.other.prop");
        }
        else {
            System.out.println("some.other.prop=" + s);
        }

    }
}
