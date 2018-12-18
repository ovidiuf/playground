package playground.spring.cloud.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({OutputChannelFactory.class})
public class KinesisBinder {
}
