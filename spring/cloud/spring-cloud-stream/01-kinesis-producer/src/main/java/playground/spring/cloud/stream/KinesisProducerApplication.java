package playground.spring.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class KinesisProducerApplication implements CommandLineRunner {

    @Autowired
    private Producer producer;

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        SpringApplication.run(KinesisProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        while(true) {

            String line = br.readLine();

            if (line.trim().isEmpty()) {

                continue;
            }

            producer.send(line);
        }

    }
}

