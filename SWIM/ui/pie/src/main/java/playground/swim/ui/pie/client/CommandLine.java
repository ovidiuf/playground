package playground.swim.ui.pie.client;

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

    void error(String s) {

        System.out.println("[error]: " + s);
        System.out.print("> ");
    }


    void multiLineOutput(String s) {

        System.out.println();
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
