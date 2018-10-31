package playground.spring.jpa.attributeConverter.command;

import playground.spring.jpa.attributeConverter.repository.ARepository;

/**
 * Deletes the record whose ID is provided.
 *
 * delete 20
 */
public class Delete implements Command {

    private long id;

    public Delete(String restOfCommandLine) {

        restOfCommandLine = restOfCommandLine.trim();

        id = Long.parseLong(restOfCommandLine);
    }

    @Override
    public void execute(ARepository aRepository) {

        aRepository.deleteById(id);

        System.out.println("deleted");
    }
}
