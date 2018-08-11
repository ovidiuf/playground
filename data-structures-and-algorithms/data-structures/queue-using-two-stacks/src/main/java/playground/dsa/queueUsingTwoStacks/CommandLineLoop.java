package playground.dsa.queueUsingTwoStacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineLoop {

    private Queue queue;
    private BufferedReader br;

    public CommandLineLoop(Queue queue) {

        this.queue = queue;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {


        while(true) {

            System.out.print("> ");

            String line = br.readLine();

            line = line.trim();

            if (line.isEmpty()) {

                continue;
            }

            try {

                if (line.startsWith("dequeue")) {

                    int i = queue.dequeue();

                    System.out.println("> " + i);


                }
                else if (line.startsWith("enqueue")) {

                    int arg = firstArgumentToInt(line);

                    queue.enqueue(arg);

                }
                else if (line.startsWith("isEmpty")) {

                    System.out.println("> " + queue.isEmpty());

                }
                else if (line.startsWith("isFull")) {

                    System.out.println("> " + queue.isFull());

                }
                else if (line.startsWith("exit")) {

                    break;
                }
                else {

                    System.out.println("> unknown command");
                }
            }
            catch(Exception e) {

                String msg = e.getMessage();
                System.out.println("> " + e.getClass().getSimpleName() + (msg == null ? "" : ": " + msg));
            }
        }

        br.close();
    }

    private int firstArgumentToInt(String line) {

        int i = line.indexOf(' ');

        if (i == -1) {

            throw new IllegalArgumentException("no first argument");
        }

        line = line.substring(i).trim();

        i = line.indexOf(' ');

        if (i != -1) {

            line = line.substring(0, i);
        }

        return Integer.parseInt(line);
    }
}
