package playground.spring.jpa.h2.command;

import playground.spring.jpa.h2.model.A;
import playground.spring.jpa.h2.repository.ARepository;

/**
 * Lists records.
 */
public class List implements Command {

    public List() {
    }

    @Override
    public void execute(ARepository aRepository) {

        aRepository.findAll().forEach(System.out::println);
    }
}
