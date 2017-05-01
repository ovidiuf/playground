package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.Duration;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.io.IOException;

/** Print a Duration as a long (milliseconds). */
final class DurationSerializer extends JsonSerializer<Duration>
{
    /** The single instance. */
    private static final DurationSerializer INSTANCE = new DurationSerializer();

    /** Return an instance. */
    public static DurationSerializer create()
    {
        return INSTANCE;
    }

    /** Default constructor. */
    private DurationSerializer()
    {
        super();
    }

    @Override
    public void serialize(
            final Duration duration,
            final JsonGenerator jgen,
            final SerializerProvider provider) throws IOException, JsonProcessingException
    {
        if (duration == null)
        {
            jgen.writeNull();
            return;
        }

        final PeriodFormatter formatter = ISOPeriodFormat.standard();
        jgen.writeString(duration.toPeriod().toString(formatter));
    }
}
