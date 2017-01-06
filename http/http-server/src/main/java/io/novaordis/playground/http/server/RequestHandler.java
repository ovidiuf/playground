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

import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Handles a HttpRequest by turning it into a HttpResponse.
 *
 * @see ConnectionHandler
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class RequestHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private File documentRoot;

    // Constructors ----------------------------------------------------------------------------------------------------

    public RequestHandler(File documentRoot) {

        if (documentRoot == null) {

            throw new IllegalArgumentException("null document root");
        }

        this.documentRoot = documentRoot;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public HttpResponse processRequest(HttpRequest request) {

        String path = request.getPath();

        if (path.startsWith("/")) {

            path = path.substring(1);
        }

        File file = new File(documentRoot, path);

        if (!file.isFile() || !file.canRead()) {

            return new HttpResponse(HttpStatusCode.NOT_FOUND);
        }

        HttpResponse response = new HttpResponse();

        //
        // read the content of the file and return it with a response. It only works with small files, of course
        //

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int r;
            byte[] buffer = new byte[1024];
            while((r = fis.read(buffer)) != -1) {

                baos.write(buffer, 0, r);
            }

            response.setEntityBodyContent(baos.toByteArray()); // this will also set Content-Length
            response.setStatusCode(HttpStatusCode.OK);
            return response;
        }
        catch(Exception e) {

            throw new RuntimeException("NOT YET IMPLEMENTED: we don't know to handle exception", e);
        }
        finally {

            if (fis != null) {

                try {

                    fis.close();
                }
                catch(IOException e) {

                    log.warn("failed to close file input stream corresponding to " + file, e);
                }
            }
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
