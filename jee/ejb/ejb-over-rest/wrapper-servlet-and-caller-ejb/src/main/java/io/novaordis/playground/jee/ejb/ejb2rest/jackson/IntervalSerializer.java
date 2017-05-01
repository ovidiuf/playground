package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.Interval;

import java.io.IOException;

/** Map Joda {@link Interval} to a pair of millisecond values. */
final class IntervalSerializer extends JsonSerializer<Interval>
{
    /** The single instance. */
    private static final IntervalSerializer INSTANCE = new IntervalSerializer();

    /** Return the instance. */
    public static IntervalSerializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance. */
    private IntervalSerializer()
    {
        super();
    }

    @Override
    public void serialize(
            final Interval interval,
            final JsonGenerator jgen,
            final SerializerProvider provider) throws IOException, JsonProcessingException
    {
        if (interval == null)
        {
            jgen.writeNull();
            return;
        }

        jgen.writeString(interval.getStartMillis() + "-" + interval.getEndMillis());
    }
}
