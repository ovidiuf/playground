package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class StreamEventConsumer {

    @StreamListener(InputChannelFactory.INPUT_CHANNEL_NAME)
    public void handle(Message e) {

        System.out.println("> received '" + e + "'");

        byte[] payload = (byte[])e.getPayload();

        System.out.println(new String(payload));
    }
}
