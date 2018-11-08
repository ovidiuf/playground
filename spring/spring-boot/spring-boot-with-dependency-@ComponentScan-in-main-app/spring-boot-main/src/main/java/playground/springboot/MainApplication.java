package playground.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import some.experimental.dependency.Dependency;

@SpringBootApplication
@ComponentScans(@ComponentScan(basePackages = "some.experimental.dependency"))
public class MainApplication implements CommandLineRunner {

    @Autowired
    private MainApplicationComponent mainApplicationComponent;

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {

        new Dependency().run();

        mainApplicationComponent.run();
    }
}
