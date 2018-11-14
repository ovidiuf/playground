package playground.json.jackson.jsonFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ISO8601DateSerializer<Date> extends JsonSerializer<Date> {

    private static final DateFormat ISO8601UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    static {

        ISO8601UTC.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        // we need to clone as DateFormat implementations are not thread safe
        gen.writeString(((DateFormat)ISO8601UTC.clone()).format(value));
    }
}
