package playground.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.springboot.dependency.Dependency;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Dependency d = new Dependency();

        System.out.println(": " + d);


    }
}
