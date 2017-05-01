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
