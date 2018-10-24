package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import playground.spring.cloud.kinesis.springcloudstreamkinesis.data.KinesisEvent;

@Component
public class StreamEventConsumer {

    @StreamListener(InputChannelFactory.INPUT_CHANNEL_NAME)
    public void handle(KinesisEvent e) {

        System.out.println("> received '" + e.getText() + "'");
    }
}
