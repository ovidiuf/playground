package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.LocalDate;

import java.io.IOException;

/** Map long values to Joda {@link LocalDate} values. */
final class LocalDateSerializer extends JsonSerializer<LocalDate>
{
    /** The single instance. */
    private static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

    /** Return the instance. */
    public static LocalDateSerializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance. */
    private LocalDateSerializer()
    {
        super();
    }

    @Override
    public void serialize(
            final LocalDate date,
            final JsonGenerator jgen,
            final SerializerProvider provider) throws IOException, JsonProcessingException
    {
        if (date == null)
        {
            jgen.writeNull();
            return;
        }

        jgen.writeString(date.toString());
    }
}
