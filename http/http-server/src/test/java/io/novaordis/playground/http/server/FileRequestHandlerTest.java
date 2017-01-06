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

package io.novaordis.playground.http.server;

import io.novaordis.playground.http.server.http.header.HttpEntityHeader;
import io.novaordis.playground.http.server.http.header.HttpHeader;
import io.novaordis.playground.http.server.http.HttpMethod;
import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.HttpStatusCode;
import io.novaordis.utilities.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class FileRequestHandlerTest extends RequestHandlerTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    protected File scratchDirectory;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    @Before
    public void before() throws Exception {

        String projectBaseDirName = System.getProperty("basedir");
        scratchDirectory = new File(projectBaseDirName, "target/test-scratch");
        assertTrue(scratchDirectory.isDirectory());
    }

    @After
    public void after() throws Exception {

        //
        // scratch directory cleanup
        //

        assertTrue(Files.rmdir(scratchDirectory, false));
    }

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void processRequest_GET_NoSuchFile() throws Exception {

        FileRequestHandler h = new FileRequestHandler(new File("."));

        HttpRequest request = new HttpRequest(HttpMethod.GET, "/I/am/sure/there/is/no/such/file.txt");

        HttpResponse response = h.processRequest(request);

        assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void processRequest_GET_FileExists() throws Exception {

        File file = new File(scratchDirectory, "test.txt");
        assertTrue(Files.write(file, "test-content\n"));

        FileRequestHandler rh = new FileRequestHandler(scratchDirectory);

        HttpRequest request = new HttpRequest(HttpMethod.GET, "/test.txt");

        HttpResponse response = rh.processRequest(request);

        assertEquals(HttpStatusCode.OK, response.getStatusCode());

        List<HttpHeader> header = response.getHeader(HttpEntityHeader.CONTENT_LENGTH);

        assertEquals(1, header.size());
        HttpHeader h = header.get(0);
        assertEquals("13", h.getFieldBody());
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    @Override
    protected FileRequestHandler getRequestHandlerToTest() {

        return new FileRequestHandler(new File("."));
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
