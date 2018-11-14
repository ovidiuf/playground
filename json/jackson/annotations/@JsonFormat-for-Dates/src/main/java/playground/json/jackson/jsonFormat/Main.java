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
        System.out.println(new String(content));
    }
}
