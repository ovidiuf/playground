package playground.spring.jpa.h2.command;

import playground.spring.jpa.h2.repository.ARepository;

public interface Command {

    void execute(ARepository aRepository) throws Exception;
}
