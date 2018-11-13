package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InputChannelFactory {

    String INPUT_CHANNEL_NAME = "channel-from-kinesis";

    @Input(INPUT_CHANNEL_NAME)
    SubscribableChannel inputChannel();
}
