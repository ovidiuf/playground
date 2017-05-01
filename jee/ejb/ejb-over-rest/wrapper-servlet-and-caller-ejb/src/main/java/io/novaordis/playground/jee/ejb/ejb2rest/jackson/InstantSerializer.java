package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/** Map Joda {@link Instant} to a long. */
final class InstantSerializer extends JsonSerializer<Instant>
{
    /** The single instance. */
    private static final InstantSerializer INSTANCE = new InstantSerializer();

    /** Return the instance. */
    public static InstantSerializer create()
    {
        return INSTANCE;
    }

    /** Default constructor. */
    private InstantSerializer()
    {
        super();
    }

    @Override
    public void serialize(
            final Instant dateTime,
            final JsonGenerator jgen,
            final SerializerProvider provider) throws IOException, JsonProcessingException
    {
        if (dateTime == null)
        {
            jgen.writeNull();
            return;
        }

        final DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        jgen.writeString(dateTime.toString(formatter));
    }
}
