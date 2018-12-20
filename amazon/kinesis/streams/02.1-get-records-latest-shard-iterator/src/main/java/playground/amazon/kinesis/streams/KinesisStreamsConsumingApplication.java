package playground.amazon.kinesis.streams;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KinesisStreamsConsumingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KinesisStreamsConsumingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Consumer c = new Consumer("ovidiu-test");

        c.run();
    }
}

