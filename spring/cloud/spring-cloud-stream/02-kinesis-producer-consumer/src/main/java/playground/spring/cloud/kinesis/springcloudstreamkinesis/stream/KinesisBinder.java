package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({OutputChannelFactory.class, InputChannelFactory.class})
public class KinesisBinder {
}
