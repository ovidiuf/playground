package playground.spring.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJpaRepositoryApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaRepositoryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println(".");

    }
}

