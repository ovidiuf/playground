package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @StreamListener(InputChannelFactory.INPUT_CHANNEL_NAME)
    public void handle(Message<String> e) {

        String payload = e.getPayload();

        System.out.println("payload: " + payload);
        System.out.println("headers:");

        MessageHeaders headers = e.getHeaders();

        for(String key: headers.keySet()) {

            System.out.println("  " + key + "=" +  headers.get(key));
        }
    }
}
