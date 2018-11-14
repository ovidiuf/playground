package playground.json.jackson.jsonFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Main {

    public static final DateFormat TEST_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");

    static {

        TEST_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) throws Exception {

        javaToJson();

        jsonToJava();
    }

    private static void javaToJson() throws Exception {

        BusinessEvent value = new BusinessEvent();
        value.setTimestamp(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        objectMapper.writeValue(baos, value);

        byte[] content = baos.toByteArray();
        String s = new String(content);

        System.out.println(s);
    }

    private static void jsonToJava() throws Exception {

        String timestamp = "2018-11-14T06:28:56.374918Z";

        String json = "{\"timestamp\":\"" + timestamp + "\"}";

        ObjectMapper objectMapper = new ObjectMapper();

        BusinessEvent be = objectMapper.readValue(json, BusinessEvent.class);

        System.out.println(timestamp);
        System.out.println(TEST_FORMAT.format(be.getTimestamp()));
    }

}
