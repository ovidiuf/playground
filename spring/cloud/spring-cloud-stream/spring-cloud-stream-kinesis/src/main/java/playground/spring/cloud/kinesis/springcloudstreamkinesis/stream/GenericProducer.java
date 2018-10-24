package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import playground.spring.cloud.kinesis.springcloudstreamkinesis.data.GenericData;

@Component
public class GenericProducer {

    private GenericProcessor processor;

    @Autowired
    public GenericProducer(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") GenericProcessor processor) {

        this.processor = processor;
    }

    public void sendData(String data) {

        GenericData gd = new GenericData(data);

        processor.dataOut().send(new GenericMessage<>(gd));
    }
}
