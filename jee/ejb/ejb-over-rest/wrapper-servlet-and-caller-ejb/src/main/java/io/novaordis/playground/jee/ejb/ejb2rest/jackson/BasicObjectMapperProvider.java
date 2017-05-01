package io.novaordis.playground.jee.ejb.ejb2rest.jackson;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.map.ext.JodaDeserializers;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;

import javax.ws.rs.ext.ContextResolver;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ServiceLoader;

/**
 * This context resolver provides a Jackson ObjectMapper that includes serializers for Joda types
 * commonly used in the MP code base. In particular, it adds support for all Jackson provided Joda
 * types (DateTime, Instants, LocalTime, etc.) plus key types that not provided by Jackson, e.g.
 * Duration.
 *
 * <code>null</code> map keys are also configured to be written as an empty string.
 */
public final class BasicObjectMapperProvider implements ContextResolver<ObjectMapper> {

    // Constants -------------------------------------------------------------------------------------------------------

    /**
     * The single instance.
     */
    private static final BasicObjectMapperProvider INSTANCE = new BasicObjectMapperProvider();

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Search for any registered JsonDeserialize instances that have been declared using the
     * ServiceLoader. Add any found to the given mapper in a 'magic' module.
     */
    private static void addDiscoverableDeserializers(final ObjectMapper mapper) {

        final ServiceLoader<JsonDeserializer> loader = ServiceLoader.load(JsonDeserializer.class);
        final Iterator<JsonDeserializer> iterator = loader.iterator();
        final SimpleModule magic = new SimpleModule("magic", new Version(1, 0, 0, ""));

        while (iterator.hasNext()) {

            final JsonDeserializer<?> deserializer = iterator.next();

            try {
                final Method deserialeMethod = deserializer.getClass()
                        .getDeclaredMethod("deserialize", JsonParser.class, DeserializationContext.class);
                final Class<?> jsonType = deserialeMethod.getReturnType();
                //noinspection unchecked
                magic.addDeserializer(jsonType, (JsonDeserializer) deserializer);
            }
            catch(Exception e) {
                throw new IllegalStateException(e);
            }
        }

        mapper.registerModule(magic);
    }

    /**
     * Add Joda-Time deserializers to a module.
     */
    private static void addJodaDeserializers(final SimpleModule module) {

        for (final StdDeserializer<?> deserializer : new JodaDeserializers().provide()) {
            module.addDeserializer((Class) deserializer.getValueClass(), deserializer);
        }
        module.addDeserializer(Duration.class, DurationDeserializer.create());
        module.addDeserializer(Instant.class, InstantDeserializer.create());
        module.addDeserializer(ReadableInstant.class, InstantDeserializer.create());
        module.addDeserializer(Interval.class, IntervalDeserializer.create());
        module.addDeserializer(LocalDate.class, LocalDateDeserializer.create());
    }

    /** Add Joda-Time serializers to a module. */
    @SuppressWarnings({
            "unchecked",
            "rawtypes"
    })
    private static void addJodaSerializers(final SimpleModule module) {

        for (final Entry<Class<?>, JsonSerializer<?>> serializer : new JodaSerializers().provide()) {
            module.addSerializer((Class) serializer.getKey(), serializer.getValue());
        }
        module.addSerializer(Duration.class, DurationSerializer.create());
        module.addSerializer(Instant.class, InstantSerializer.create());
        module.addSerializer(Interval.class, IntervalSerializer.create());
        module.addSerializer(LocalDate.class, LocalDateSerializer.create());
    }

    /** Return the singleton instance. */
    public static BasicObjectMapperProvider getInstance()
    {
        return INSTANCE;
    }

    /** Return a new module for serializing and deserializing Joda-Time classes. */
    private static SimpleModule newJodaModule() {
        final SimpleModule joda = new SimpleModule("Joda", new Version(1, 0, 0, null));
        addJodaDeserializers(joda);
        addJodaSerializers(joda);
        return joda;
    }

    /** Create a new object mapper. */
    private static ObjectMapper newMapper() {

        final ObjectMapper aMapper = new ObjectMapper();
        aMapper.registerModule(newJodaModule());
        aMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
        aMapper.getSerializerProvider()
            .setNullKeySerializer(NullKeysAsEmptyStringsSerializer.create());

        aMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        addDiscoverableDeserializers(aMapper);
        return aMapper;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    /** The mapper that will be provided when {@link #getContext(Class)} is invoked. */
    private final ObjectMapper mapper;

    // Constructors ----------------------------------------------------------------------------------------------------

    /** Constructor that initializes the mapper. */
    private BasicObjectMapperProvider()
    {
        mapper = newMapper();
    }

    public BasicObjectMapperProvider(ObjectMapper mapper) {

        this.mapper = mapper;
    }

    // ContextResolver implementation ----------------------------------------------------------------------------------

    @Override
    public ObjectMapper getContext(final Class<?> ignored) {

        return getMapper();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public ObjectMapper getMapper() {

        return mapper;
    }

}
