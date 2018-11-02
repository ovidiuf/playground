package playground.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.springboot.dependency.Dependency;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Autowired
    private SpringApplicationContextBootstrapForDependencies springBootstrap;

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {

        springBootstrap.prepareContextForDependencies();

        new Dependency().run();

    }
}
