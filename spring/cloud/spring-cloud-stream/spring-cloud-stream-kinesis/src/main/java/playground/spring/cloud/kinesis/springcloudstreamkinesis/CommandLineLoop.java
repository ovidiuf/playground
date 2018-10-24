package playground.spring.cloud.kinesis.springcloudstreamkinesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.spring.cloud.kinesis.springcloudstreamkinesis.stream.StreamEventProducer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class CommandLineLoop {

    private final BufferedReader br;

    private StreamEventProducer producer;

    @Autowired
    public CommandLineLoop(StreamEventProducer producer) {

        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.producer = producer;
    }

    void run() throws Exception {

        while(true) {

            System.out.print("> ");

            String line = br.readLine();

            producer.sendData(line);

            System.out.println(": '" + line + "' sent");
        }

    }
}
