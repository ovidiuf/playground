package playground.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SbexampleApplication implements CommandLineRunner {

    @Autowired
    private ExampleConfigurationProperties configurationProperties;

    public static void main(String[] args) {
        SpringApplication.run(SbexampleApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("color: " + configurationProperties.getColor());
    }
}
