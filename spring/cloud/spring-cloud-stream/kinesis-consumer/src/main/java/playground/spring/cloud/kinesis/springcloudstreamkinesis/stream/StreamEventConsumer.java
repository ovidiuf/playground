package playground.spring.cloud.kinesis.springcloudstreamkinesis.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import playground.spring.cloud.kinesis.springcloudstreamkinesis.JsonRenderer;

import java.util.Map;

@Component
public class StreamEventConsumer {

    @StreamListener(InputChannelFactory.INPUT_CHANNEL_NAME)
    public void handle(Message e) {

        System.out.println("> received '" + e + "'");

        byte[] payload = (byte[])e.getPayload();

        ObjectMapper om = new ObjectMapper();

        Map map = null;

        try {

            map = om.readValue(payload, Map.class);

        }
        catch(Exception ex) {

            ex.printStackTrace();
        }

        if (map == null) {

            System.err.println("message payload deserialized to a null map");
        }
        else {

            StringBuilder sb = new StringBuilder();
            JsonRenderer.renderJson(null, map, 0, sb);
            System.out.println(sb.toString());

        }
    }
}
