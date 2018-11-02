package playground.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.dependency.Dependency;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    //
    // Autowiring SpringApplicationContextConfiguratorForDependencies into the main application will insure the
    // component is created early, and as consequence, it configures the dependency's application context access.
    //
    @Autowired
    private SpringApplicationContextConfiguratorForDependencies springBootstrap;

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {

        new Dependency().run();

    }
}
