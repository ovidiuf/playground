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

package io.novaordis.playground.http.server.rhandler;

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
 * Handles requests by attempting to find the corresponding files in root and resolve them.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class FileRequestHandler implements RequestHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(FileRequestHandler.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private File documentRoot;

    // Constructors ----------------------------------------------------------------------------------------------------

    public FileRequestHandler(File documentRoot) {

        if (documentRoot == null) {

            throw new IllegalArgumentException("null document root");
        }

        this.documentRoot = documentRoot;
    }

    // RequestHandler implementation -----------------------------------------------------------------------------------

    @Override
    public boolean accepts(HttpRequest request) {

        return true;
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {

        HttpResponse response = new HttpResponse();
        response.setRequest(request);

        String path = request.getPath();

        File file = resolveFile(path);

        if (file == null) {

            response.setStatusCode(HttpStatusCode.NOT_FOUND);
            return response;
        }

        //
        // read the content of the file and return it with a response. It only works with small files, of course
        //

        byte[] content = readContentFromFile(file);
        response.setBody(content); // this will also set Content-Length
        response.setStatusCode(HttpStatusCode.OK);
        return response;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    byte[] readContentFromFile(File file) {

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int r;
            byte[] buffer = new byte[1024];
            while((r = fis.read(buffer)) != -1) {

                baos.write(buffer, 0, r);
            }

            return baos.toByteArray();
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

    /**
     * @return null if the file does not exist, is not a file or cannot be read.
     */
    File resolveFile(String path) {

        if (path.startsWith("/")) {

            path = path.substring(1);
        }

        File file = new File(documentRoot, path);

        if (!file.isFile() || !file.canRead()) {

            return null;
        }

        return file;
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
