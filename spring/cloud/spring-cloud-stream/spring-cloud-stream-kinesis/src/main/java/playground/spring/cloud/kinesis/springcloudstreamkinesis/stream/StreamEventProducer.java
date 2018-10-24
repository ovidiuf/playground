package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import playground.spring.cloud.kinesis.springcloudstreamkinesis.data.KinesisEvent;

@Component
public class StreamEventProducer {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private OutputChannelFactory outputChannelFactory;

    public void sendData(String payload) {

        KinesisEvent e = new KinesisEvent(payload);

        outputChannelFactory.outputChannel().send(new GenericMessage<>(e));
    }
}
