package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/** Convert <code>null</code> map keys to be empty strings. */
final class NullKeysAsEmptyStringsSerializer extends JsonSerializer<Object> {

    /** The single instance. */
    private static final NullKeysAsEmptyStringsSerializer INSTANCE =
            new NullKeysAsEmptyStringsSerializer();

    /** Return the single instance. */
    public static NullKeysAsEmptyStringsSerializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance. */
    private NullKeysAsEmptyStringsSerializer()
    {
        super();
    }

    @Override
    public void serialize(
            final Object nullKey,
            final JsonGenerator jsonGenerator,
            final SerializerProvider unused) throws IOException, JsonProcessingException {
        jsonGenerator.writeFieldName("");
    }
}
