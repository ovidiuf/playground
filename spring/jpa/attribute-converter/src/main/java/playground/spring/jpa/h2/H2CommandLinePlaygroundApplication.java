package playground.spring.jpa.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class H2CommandLinePlaygroundApplication implements CommandLineRunner {

    @Autowired
    private CommandLineLoop loop;

    public static void main(String[] args) throws Exception {

        org.h2.tools.Server server = org.h2.tools.Server.createTcpServer().start();

        SpringApplication.run(H2CommandLinePlaygroundApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        loop.run();

        System.out.println("exiting ...");

        System.exit(0);
    }
}
