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
