package playground.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import some.experimental.dependency.Dependency;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {

        new Dependency().run();
    }
}
