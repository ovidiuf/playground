package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import org.joda.time.LocalDate;

import java.io.IOException;

/** Map long values to Joda {@link LocalDate} values. */
final class LocalDateDeserializer extends StdScalarDeserializer<LocalDate>
{
    /** The single instance. */
    private static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();

    /** Return the instance. */
    public static LocalDateDeserializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance. */
    private LocalDateDeserializer()
    {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws JsonParseException, IOException
    {
        final String text = parser.getText();
        if (text == null)
        {
            return null;
        }
        return new LocalDate(text);
    }
}
