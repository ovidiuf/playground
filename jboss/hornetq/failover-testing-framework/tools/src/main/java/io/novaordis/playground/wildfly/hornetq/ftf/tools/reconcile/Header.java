/*
 * Copyright (c) 2016 Nova Ordis LLC
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

package io.novaordis.playground.wildfly.hornetq.ftf.tools.reconcile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class Header {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // empty header is legal
    private List<String> headers;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Header(String line) {

        this.headers = new ArrayList<>();

        String[] hs = line.split(",");

        for(String h: hs) {
            h = h.trim();
            headers.add(h);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Header)) {
            return false;
        }

        Header that = (Header)o;

        if (headers.size() != that.headers.size()) {
            return false;
        }

        for(int i = 0; i < headers.size(); i ++) {
            if (!headers.get(i).equals(that.headers.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {

        int h = 17;

        for(String s: headers) {
            h += 3 * s.hashCode();
        }

        return h;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
