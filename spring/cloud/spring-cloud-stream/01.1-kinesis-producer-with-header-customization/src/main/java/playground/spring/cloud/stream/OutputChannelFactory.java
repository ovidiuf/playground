package playground.spring.cloud.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputChannelFactory {

    String OUTPUT_CHANNEL_NAME = "test-output-channel";

    @Output(OUTPUT_CHANNEL_NAME)
    MessageChannel outputChannel();
}
