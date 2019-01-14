package playground.spring.jpa.h2.command;

import playground.spring.jpa.h2.model.A;
import playground.spring.jpa.h2.repository.ARepository;

import java.util.Optional;

/**
 * Queries by name.
 *
 * <p>
 * findByName blah
 */
public class FindByName implements Command {

    private String name;

    public FindByName(String restOfCommandLine) {

        restOfCommandLine = restOfCommandLine.trim();

        if (restOfCommandLine.isEmpty()) {

            throw new RuntimeException("name missing");
        }

        int i = restOfCommandLine.indexOf(' ');

        if (i == -1) {

            name = restOfCommandLine;
        }
        else {

            name = restOfCommandLine.substring(0, i);
        }
    }

    @Override
    public void execute(ARepository aRepository) {

        A result = aRepository.findByName(name);

        System.out.println(result);
    }
}
