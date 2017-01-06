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

package io.novaordis.playground.http.server.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class MockConnection extends Connection {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private List<byte[]> flushedOutputContent;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MockConnection(long id) throws IOException {

        super(id, new MockSocket(), null);
        this.flushedOutputContent = new ArrayList<>();
    }

    public MockConnection(long id, String requestContent) throws IOException {

        super(id, new MockSocket(requestContent), null);
        this.flushedOutputContent = new ArrayList<>();
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public void flush() {

        //
        // save flushed content
        //

        recordOutputChunkAccumulatedSoFar();
    }

    @Override
    public void close() {

        //
        // save flushed content
        //

        recordOutputChunkAccumulatedSoFar();

        super.close();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * @return the chunks of content followed by flushes. close() count as a flush. However, if close() follows
     * immediately after a flush(), no byte[0] will be added to the list.
     */
    public List<byte[]> getFlushedContent() {

        return flushedOutputContent;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void recordOutputChunkAccumulatedSoFar() {

        MockSocket ms = (MockSocket)getSocket();

        ByteArrayOutputStream baos = ms.getOutputStream();
        byte[] bytes = baos.toByteArray();

        if (bytes.length > 0) {

            flushedOutputContent.add(bytes);
            ms.resetOutputStream();
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
