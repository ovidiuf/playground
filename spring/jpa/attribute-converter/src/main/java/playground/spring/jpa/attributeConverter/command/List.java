package playground.spring.jpa.attributeConverter.command;

import playground.spring.jpa.attributeConverter.repository.ARepository;

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
