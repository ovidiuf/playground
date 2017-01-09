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

package io.novaordis.playground.http.server.http;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/17
 */
public class QueryTest {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(QueryTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void emptyQuery() throws Exception {

        Query q = new Query();
        assertNull(q.getParameter("something"));
    }

    @Test
    public void query() throws Exception {

        Query q = new Query("a=b");
        assertNull(q.getParameter("something"));
        assertEquals("b", q.getParameter("a"));
    }

    @Test
    public void query2() throws Exception {

        Query q = new Query("a=b&c=d");
        assertNull(q.getParameter("something"));
        assertEquals("b", q.getParameter("a"));
        assertEquals("d", q.getParameter("c"));
    }

    @Test
    public void query_PairMissingEqualsSign() throws Exception {

        try {
            new Query("blah");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("invalid HTTP request query section: \"blah\"", msg);
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
