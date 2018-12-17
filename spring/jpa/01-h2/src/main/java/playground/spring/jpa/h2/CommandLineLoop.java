package playground.spring.jpa.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.spring.jpa.h2.command.Create;
import playground.spring.jpa.h2.command.Delete;
import playground.spring.jpa.h2.command.Help;
import playground.spring.jpa.h2.command.List;
import playground.spring.jpa.h2.command.Update;
import playground.spring.jpa.h2.repository.ARepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@SuppressWarnings("WeakerAccess")
public class CommandLineLoop {

    private ARepository aRepository;

    private BufferedReader br;

    @Autowired
    public CommandLineLoop(ARepository aRepository) {

        this.aRepository = aRepository;

        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws Exception {

        while (true) {

            System.out.print("> ");

            String line = br.readLine().trim();

            if (line.isEmpty()) {

                continue;
            }

            if (line.toLowerCase().startsWith("exit")) {

                break;
            }

            handleCommand(line);
        }
    }

    private void handleCommand(String line) {

        String hint = line.trim().toLowerCase();

        try {

            if (hint.startsWith("create")) {

                new Create(line.substring("insert".length())).execute(aRepository);
            } else if (hint.startsWith("update")) {

                new Update(line.substring("update".length())).execute(aRepository);
            } else if (hint.startsWith("delete")) {

                new Delete(line.substring("delete".length())).execute(aRepository);
            } else if (hint.startsWith("list")) {

                new List().execute(aRepository);
            } else if (hint.startsWith("help")) {

                new Help().execute(null);
            } else {

                throw new IllegalArgumentException("unknown command: " + hint);
            }

        } catch (Exception e) {

            System.err.println("[error]: " + e.getMessage() + "\n> ");
        }
    }
}
