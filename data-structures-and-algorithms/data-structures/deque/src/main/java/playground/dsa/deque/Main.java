package playground.dsa.deque;

public class Main {

    public static void main(String[] args) throws Exception {

        Deque deque = new Deque(4);

        CommandLineLoop commandLineLoop = new CommandLineLoop(deque);

        commandLineLoop.run();
    }
}
