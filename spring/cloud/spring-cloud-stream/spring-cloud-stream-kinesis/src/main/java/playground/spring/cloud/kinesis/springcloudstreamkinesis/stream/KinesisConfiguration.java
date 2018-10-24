package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import playground.spring.cloud.kinesis.springcloudstreamkinesis.data.GenericData;

@EnableBinding(GenericProcessor.class)
public class KinesisConfiguration {

    @StreamListener(GenericProcessor.INPUT)
    public void processData(GenericData data) {

        System.out.println(">>>>>>>>>>>> " + data.getText());

    }
}
