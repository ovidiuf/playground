package playground.dsa.stack;

public class Main {

    public static void main(String[] args) throws Exception {

        Stack stack = new Stack(5);

        CommandLineLoop commandLineLoop = new CommandLineLoop(stack);

        commandLineLoop.run();
    }
}
