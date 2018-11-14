package playground.json.jackson.jsonFormat;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Main {

    public static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");

    static {

        FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

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
                        "\"timestamp\":\"11/14/18 06:28:56+0000\"," +
                        "\"payload\":{" +
                        "   \"expiresAt\":\"2018-11-15T06:28:56.348Z\"" +
                        "}" +
                "}";

        BusinessEvent e2 = objectMapper.readValue(json.getBytes(), BusinessEvent.class);
        System.out.println("timestamp: " + FORMAT.format(e2.getTimestamp()));
        System.out.println("expires at: " + FORMAT.format(e2.getPayload().getExpiresAt()));
    }
}
