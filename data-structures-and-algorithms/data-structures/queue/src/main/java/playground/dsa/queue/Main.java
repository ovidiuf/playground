package playground.dsa.queue;

public class Main {

    public static void main(String[] args) throws Exception {

        Queue queue = new QueueNMinusOne(3);

        CommandLineLoop commandLineLoop = new CommandLineLoop(queue);

        commandLineLoop.run();
    }
}
