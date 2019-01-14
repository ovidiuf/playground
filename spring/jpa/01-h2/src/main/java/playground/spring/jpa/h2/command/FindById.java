package playground.spring.jpa.h2.command;

import playground.spring.jpa.h2.model.A;
import playground.spring.jpa.h2.repository.ARepository;

import java.util.Optional;

/**
 * Queries by ID.
 *
 * <p>
 * findById 10
 */
public class FindById implements Command {

    private long id;

    public FindById(String restOfCommandLine) {

        restOfCommandLine = restOfCommandLine.trim();

        id = Long.parseLong(restOfCommandLine);
    }

    @Override
    public void execute(ARepository aRepository) {

        Optional<A> result = aRepository.findById(id);

        System.out.println(result);
    }
}
