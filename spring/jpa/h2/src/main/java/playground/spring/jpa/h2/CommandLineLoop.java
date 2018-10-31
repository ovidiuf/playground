package playground.spring.jpa.h2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SuppressWarnings("WeakerAccess")
public class CommandLineLoop {

    private BufferedReader br;

    public CommandLineLoop() {

        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws Exception {

        while(true) {

            System.out.print("> ");

            String line = br.readLine().trim();

            if (line.isEmpty()) {

                continue;
            }

            if (line.toLowerCase().startsWith("exit")) {

                break;
            }
        }
    }
}
