package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputChannelFactory {

    String OUTPUT_CHANNEL_NAME = "channel-to-kinesis";

    @Output(OUTPUT_CHANNEL_NAME)
    MessageChannel outputChannel();
}
