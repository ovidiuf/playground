package playground.json.jackson.fullDataBinding;

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

        //
        // Java to JSON - this is what Spring Messaging is using to serialize Messages
        //

        DateFormat iso8601_UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        iso8601_UTC.setTimeZone(TimeZone.getTimeZone("UTC"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(iso8601_UTC);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

        JsonEncoding encoding = JsonEncoding.UTF8;
        JsonGenerator generator = objectMapper.getFactory().createGenerator(baos, encoding);

        Class<?> view = null;

        if (view != null) {

            objectMapper.writerWithView(view).writeValue(generator, value);
        }
        else {

            objectMapper.writeValue(generator, value);
        }

        byte[] content = baos.toByteArray();
        System.out.println(new String(content));
        
        //
        // alternative when we want to generate String, not byte[]
        //
        // Writer writer = new StringWriter();
        // if (view != null) {
        //   this.objectMapper.writerWithView(view).writeValue(writer, payload);
        // }
        // else {
        //   this.objectMapper.writeValue(writer, payload);
        // }
        // payload = writer.toString();

    }
}
