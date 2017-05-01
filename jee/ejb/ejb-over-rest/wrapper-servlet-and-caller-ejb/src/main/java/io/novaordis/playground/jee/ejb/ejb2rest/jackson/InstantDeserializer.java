package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/** Map longs to Joda {@link Instant}s. */
final class InstantDeserializer extends StdScalarDeserializer<Instant>
{
    /** The single instance. */
    private static final InstantDeserializer INSTANCE = new InstantDeserializer();

    /** Return the instance. */
    public static InstantDeserializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance. */
    private InstantDeserializer()
    {
        super(Instant.class);
    }

    @Override
    public Instant deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        final JsonToken jsonToken = parser.getCurrentToken();

        if (jsonToken == JsonToken.VALUE_NUMBER_INT)
        {
            return new Instant(parser.getLongValue());
        }
        else if (jsonToken == JsonToken.VALUE_STRING)
        {
            final String str = parser.getText().trim();
            if (str.length() == 0)
            {
                return null;
            }
            final DateTimeFormatter formatter = ISODateTimeFormat.dateTimeParser();
            final DateTime dateTime = formatter.parseDateTime(str);

            return new Instant(dateTime.getMillis());
        }

        throw ctxt.mappingException(Instant.class);
    }
}
