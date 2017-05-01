package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import org.joda.time.Duration;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.io.IOException;

/** Parse a long (milliseconds) to a Joda Duration. */
final class DurationDeserializer extends StdScalarDeserializer<Duration>
{
    /** The single instance. */
    private static final DurationDeserializer INSTANCE = new DurationDeserializer();

    /** Return the single instance. */
    public static DurationDeserializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance */
    private DurationDeserializer()
    {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws JsonParseException, IOException
    {
        final JsonToken jsonToken = parser.getCurrentToken();

        if (jsonToken == JsonToken.VALUE_NUMBER_INT)
        {
            return new Duration(parser.getLongValue());
        }
        else if (jsonToken == JsonToken.VALUE_STRING)
        {
            final String str = parser.getText().trim();
            if (str.length() == 0)
            {
                return null;
            }
            final PeriodFormatter formatter = ISOPeriodFormat.standard();
            return formatter.parsePeriod(str).toStandardDuration();
        }

        throw ctxt.mappingException(Duration.class);
    }
}
