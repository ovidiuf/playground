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

package io.novaordis.playground.java.network.traffic;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/16/17
 */
public enum AddressType {

    // Constants -------------------------------------------------------------------------------------------------------

    IPv4,
    IPv6,
    ;

    // Static ----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public boolean match(InetAddress a) {

        if (a == null) {

            return false;
        }

        if (a instanceof Inet4Address) {

            return IPv4.equals(this);
        }
        else if (a instanceof Inet6Address) {

            return IPv6.equals(this);
        }
        else {

            throw new RuntimeException("NOT YET IMPLEMENTED: " + a);
        }
    }

}
