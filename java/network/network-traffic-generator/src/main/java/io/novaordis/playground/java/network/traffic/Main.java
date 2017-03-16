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

import io.novaordis.playground.java.network.traffic.multicast.MulticastReceiver;
import io.novaordis.playground.java.network.traffic.multicast.MulticastSender;
import io.novaordis.playground.java.network.traffic.udp.UDPReceiver;
import io.novaordis.playground.java.network.traffic.udp.UDPSender;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {


        try {

            Configuration c = new Configuration(args);

            Mode m = c.getMode();

            if (m.isInfo()) {

                //
                // display info about the networking context and exit
                //

                Util.displayInfo();
                return;
            }
            else if (m.isTest()) {

                ExperimentalCode.execute();
                return;
            }

            c.validate();

            Protocol p = c.getProtocol();

            if (m.isReceive()) {

                Receiver r;

                if (p.isUDP()) {

                    r = new UDPReceiver(c);
                }
                else if (p.isMulticast()) {

                    r = new MulticastReceiver();
                }
                else {

                    throw new RuntimeException("NOT YET IMPLEMENTED " + p);
                }

                r.receive();
            }
            else {

                //
                // we're sending
                //

                Sender s;

                if (p.isUDP()) {

                    s = new UDPSender(c);
                }
                else if (p.isMulticast()) {

                    s = new MulticastSender();
                }
                else {

                    throw new RuntimeException("NOT YET IMPLEMENTED " + p);
                }

                s.send();
            }
        }
        catch(UserErrorException e) {

            System.err.println("[error]: " + e.getMessage());

            Throwable cause = e.getCause();

            if (cause != null) {

                System.err.println("\n   cause:\n\n");
                cause.printStackTrace(System.err);
            }
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
