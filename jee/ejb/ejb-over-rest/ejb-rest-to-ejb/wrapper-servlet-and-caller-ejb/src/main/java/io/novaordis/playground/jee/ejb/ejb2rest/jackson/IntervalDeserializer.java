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

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import org.joda.time.Interval;
import org.slf4j.helpers.MessageFormatter;

import java.io.IOException;

/** Map two longs to a Joda {@link Interval}. */
final class IntervalDeserializer extends StdScalarDeserializer<Interval>
{
    /** The single instance. */
    private static final IntervalDeserializer INSTANCE = new IntervalDeserializer();

    /** Return the instance. */
    public static IntervalDeserializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance. */
    private IntervalDeserializer()
    {
        super(Interval.class);
    }

    @Override
    public Interval deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        final String source = parser.getText().trim();

        final int index = source.indexOf('-', 1);
        if (index < 0)
        {
            throw ctxt
                .weirdStringException(Interval.class, "No hyphen found to separate start, end.");
        }

        long start;
        long end;
        String str = source.substring(0, index);
        Interval result;

        try
        {
            start = Long.parseLong(str);
            str = source.substring(index + 1);
            end = Long.parseLong(str);
            result = new Interval(start, end);
        }
        catch (final NumberFormatException e)
        {
            final String msg = MessageFormatter.format(
                    "Failed to parse number from '{}' (full source String '{}') to construct Interval.",
                    str,
                    source)
                .getMessage();
            throw JsonMappingException.from(parser, msg, e);
        }

        return result;
    }
}
