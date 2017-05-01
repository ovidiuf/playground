/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
