package playground.json.jackson.jsonFormat;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {

        BusinessEvent value = new BusinessEvent();
        value.setType("test");
        value.setTimestamp(new Date());

        BusinessPayload bp = new BusinessPayload();
        bp.setExpiresAt(new Date(System.currentTimeMillis() + 24L * 3600 * 1000));
        value.setPayload(bp);

        ObjectMapper objectMapper = new ObjectMapper();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

        JsonEncoding encoding = JsonEncoding.UTF8;
        JsonGenerator generator = objectMapper.getFactory().createGenerator(baos, encoding);
        objectMapper.writeValue(generator, value);

        byte[] content = baos.toByteArray();

        String s = new String(content);
        System.out.println(s);

        String json =
                "{" +
                        "\"type\":\"test\"," +
                        "\"timestamp\":\"2018-11-14T06:28:56.374918Z\"," +
                        "\"payload\":{" +
                        "   \"expiresAt\":\"2018-11-15T06:28:56.348Z\"" +
                        "}" +
                "}";

        BusinessEvent e2 = objectMapper.readValue(json.getBytes(), BusinessEvent.class);

        System.out.println(e2);
    }
}
