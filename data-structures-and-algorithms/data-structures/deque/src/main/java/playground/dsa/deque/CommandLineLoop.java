package playground.dsa.deque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineLoop {

    private Deque deque;
    private BufferedReader br;

    public CommandLineLoop(Deque deque) {

        this.deque = deque;
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

                if (line.startsWith("pop")) {

                    int i = deque.pop();

                    System.out.println("> " + i);


                }
                else if (line.startsWith("push")) {

                    int arg = firstArgumentToInt(line);

                    deque.push(arg);

                }
                else if (line.startsWith("eject")) {

                    int i = deque.eject();

                    System.out.println("> " + i);


                }
                else if (line.startsWith("inject")) {

                    int arg = firstArgumentToInt(line);

                    deque.inject(arg);

                }
                else if (line.startsWith("isEmpty")) {

                    System.out.println("> " + deque.isEmpty());

                }
                else if (line.startsWith("isFull")) {

                    System.out.println("> " + deque.isFull());

                }
                else if (line.startsWith("dump")) {

                    System.out.println("> " + deque.dump());

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
