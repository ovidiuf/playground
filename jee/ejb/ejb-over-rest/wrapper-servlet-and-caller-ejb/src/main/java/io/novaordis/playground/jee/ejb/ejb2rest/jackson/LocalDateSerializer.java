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

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.LocalDate;

import java.io.IOException;

/** Map long values to Joda {@link LocalDate} values. */
final class LocalDateSerializer extends JsonSerializer<LocalDate>
{
    /** The single instance. */
    private static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

    /** Return the instance. */
    public static LocalDateSerializer create()
    {
        return INSTANCE;
    }

    /** Create a new instance. */
    private LocalDateSerializer()
    {
        super();
    }

    @Override
    public void serialize(
            final LocalDate date,
            final JsonGenerator jgen,
            final SerializerProvider provider) throws IOException, JsonProcessingException
    {
        if (date == null)
        {
            jgen.writeNull();
            return;
        }

        jgen.writeString(date.toString());
    }
}
