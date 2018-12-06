package playground.spring.propertyinjection.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnvironmentAndPropertySourcesApplication implements CommandLineRunner {

    private final CommandLineLoop commandLineLoop;

    @Autowired
    public EnvironmentAndPropertySourcesApplication(CommandLineLoop commandLineLoop) {

        this.commandLineLoop = commandLineLoop;
    }

    public static void main(String[] args) {
        SpringApplication.run(EnvironmentAndPropertySourcesApplication.class, args);
    }

    @Override
    public void run(String... args) {

        commandLineLoop.run(args);
    }
}
