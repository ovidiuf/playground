package playground.spring.jpa.h2.command;

import playground.spring.jpa.h2.model.A;
import playground.spring.jpa.h2.repository.ARepository;

import java.util.Optional;

/**
 * Updates the record with the given ID
 *
 * update 20 Menlo Park
 */
public class Update implements Command {

    private long id;
    private String name;

    public Update(String restOfCommandLine) {

        restOfCommandLine = restOfCommandLine.trim();

        int i = restOfCommandLine.indexOf(' ');

        if (i == -1) {

            throw new IllegalArgumentException("an ID and a name should be provided");
        }

        id = Long.parseLong(restOfCommandLine.substring(0, i));
        name = restOfCommandLine.substring(i + 1);

        if (name.isEmpty()) {

            throw new IllegalArgumentException("empty name");
        }
    }

    @Override
    public void execute(ARepository aRepository) {

        Optional<A> o = aRepository.findById(id);

        if (!o.isPresent()) {

            throw new IllegalArgumentException("no such ID: " + id);
        }

        A a = o.get();

        a.setName(name);

        aRepository.save(a);

        System.out.println("updated " + a);
    }
}
