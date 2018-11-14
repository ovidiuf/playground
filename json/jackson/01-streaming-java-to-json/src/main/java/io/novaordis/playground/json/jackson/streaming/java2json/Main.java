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

package io.novaordis.playground.json.jackson.streaming.java2json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.ByteArrayOutputStream;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/25/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        ByteArrayOutputStream destination = new ByteArrayOutputStream();

        JsonFactory f = new JsonFactory();

        JsonGenerator generator = f.createGenerator(destination);

        generator.writeStartObject();
        generator.writeFieldName("test-field-name");
        generator.writeString("test-field-value");
        generator.writeFieldName("test-field-name-2");
        generator.writeString("test-field-value-2");
        generator.writeFieldName("test-field-name-3");
        generator.writeStartArray();
        generator.writeString("array-element-1");
        generator.writeString("array-element-2");
        generator.writeEndArray();

        generator.writeEndObject();

        generator.close();

        System.out.println(new String(destination.toByteArray()));

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
