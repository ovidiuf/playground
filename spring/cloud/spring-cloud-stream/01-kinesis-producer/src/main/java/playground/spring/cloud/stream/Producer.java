package playground.spring.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private MessageChannel outputChannel;

    @Autowired
    public Producer(OutputChannelFactory outputChannelFactory) {

        outputChannel = outputChannelFactory.outputChannel();
    }

    public void send(String payload) {

        Message m = new GenericMessage<>(payload);
        outputChannel.send(m);
    }
}
