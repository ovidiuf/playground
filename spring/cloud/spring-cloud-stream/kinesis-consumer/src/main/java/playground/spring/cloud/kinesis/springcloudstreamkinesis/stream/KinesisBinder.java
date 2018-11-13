package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({InputChannelFactory.class})
public class KinesisBinder {
}
