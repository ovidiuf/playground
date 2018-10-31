package playground.spring.jpa.attributeConverter.command;

import playground.spring.jpa.attributeConverter.repository.ARepository;

public interface Command {

    void execute(ARepository aRepository) throws Exception;
}
