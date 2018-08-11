package playground.dsa.queueUsingTwoStacks;

public class Main {

    public static void main(String[] args) throws Exception {

        Queue queue = new QueueUsingTwoStacks(3);

        CommandLineLoop commandLineLoop = new CommandLineLoop(queue);

        commandLineLoop.run();
    }
}
