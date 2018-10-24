package playground.spring.cloud.kinesis.springcloudstreamkinesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.spring.cloud.kinesis.springcloudstreamkinesis.stream.GenericProducer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
public class SpringCloudStreamKinesisApplication implements CommandLineRunner {

    @Autowired
    private GenericProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamKinesisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            System.out.print("> ");

            String line = br.readLine();

            producer.sendData(line);

            System.out.println(": '" + line + "' sent");
        }
    }
}
