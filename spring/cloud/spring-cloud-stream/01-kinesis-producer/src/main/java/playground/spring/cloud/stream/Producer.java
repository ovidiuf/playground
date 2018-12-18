package playground.spring.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class Producer {

    private MessageChannel outputChannel;

    @Autowired
    public Producer(OutputChannelFactory outputChannelFactory) {

        outputChannel = outputChannelFactory.outputChannel();
    }

    void send(String payload) {

        Map<String, Object> headers = Collections.singletonMap("test-header-key", "test-header-value");

        Message m = new GenericMessage<>(payload, headers);

        outputChannel.send(m);
    }
}
