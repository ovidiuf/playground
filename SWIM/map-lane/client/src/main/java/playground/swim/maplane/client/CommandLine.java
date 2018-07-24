package playground.swim.maplane.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/24/18
 */
class CommandLine {

    private BufferedReader br;

    private boolean promptRequired;

    CommandLine() {

        br = new BufferedReader(new InputStreamReader(System.in));

        promptRequired = true;
    }

    void start() {

        handlePrompt();
    }

    String readLine() throws IOException {

        try {

            return br.readLine();
        }
        finally {

            promptRequired = true;
            handlePrompt();
        }
    }

    void info(String s) {

        handlePrompt();
        System.out.println("[info]: " + s);
        promptRequired = true;
        handlePrompt();
    }

    void error(String s) {

        handlePrompt();
        System.out.println("[error]: " + s);
        promptRequired = true;
        handlePrompt();
    }

    void close() {

        try {

            br.close();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void handlePrompt() {

        if (promptRequired) {

            System.out.print("> ");
            promptRequired = false;
        }
    }
}