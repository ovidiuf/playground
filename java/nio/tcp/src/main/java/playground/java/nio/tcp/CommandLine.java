package playground.java.nio.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Manages input/output from command line, printing prompts when necessary.
 *
 * @since 7/25/18
 */
class CommandLine {

    private BufferedReader br;

    private boolean reading;

    public static void main(String[] args) throws Exception {

        final Random random = new Random();

        final CommandLine cl = new CommandLine();
        cl.start();

        new Thread(() -> {

            while(true) {

                int sleep = random.nextInt(4);

                try {

                    Thread.sleep(sleep * 1000L);
                }
                catch (InterruptedException e) {

                    // noop
                }

                cl.info("I've slept " + sleep + " seconds");
            }

        }, "Writer").start();

        //
        // read loop
        //

        while(true) {

            String line = cl.readLine();

            if ("exit".equals(line)) {

                System.exit(0);
            }
        }
    }

    CommandLine() {

        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    void start() {

        System.out.print("> ");
    }

    void info(String s) {

        System.out.println(s);
        System.out.print("> ");
    }

    String readLine() throws IOException {

        reading = true;

        try {

            return br.readLine();
        }
        finally {

            System.out.print("> ");
            reading = false;
        }
    }

}
