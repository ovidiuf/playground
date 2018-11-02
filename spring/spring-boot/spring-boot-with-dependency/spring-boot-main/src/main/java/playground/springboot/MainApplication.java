package playground.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import playground.dependency.Dependency;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@SpringBootApplication
//
// this is necessary to enable component scan in the dependency package
//
@ComponentScan(basePackageClasses = {MainApplication.class, Dependency.class})
public class MainApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {

        new Dependency().run();
    }
}
