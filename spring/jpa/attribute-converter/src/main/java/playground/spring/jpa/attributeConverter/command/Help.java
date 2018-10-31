package playground.spring.jpa.attributeConverter.command;

import playground.spring.jpa.attributeConverter.repository.ARepository;

public class Help implements Command {

    public Help() {
    }

    @Override
    public void execute(ARepository aRepository) {

        String help = "\nCommands:\n\n   create <name>\n   update <id> <name>\n   delete id\n   list\n";

        System.out.println(help);
    }
}
