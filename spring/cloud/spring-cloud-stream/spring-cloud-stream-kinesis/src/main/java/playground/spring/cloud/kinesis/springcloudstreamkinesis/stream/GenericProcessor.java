package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface GenericProcessor {

    String INPUT = "dataIn";

    @Output
    MessageChannel dataOut();

    @Input
    SubscribableChannel dataIn();
}
