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

package com.novaordis.playground.google.maps.distance;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URL;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/22/16
 */
public class UsageExample {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        URL googleApiServer = new URL("https://maps.googleapis.com");
        String path = "/maps/api/distancematrix/json?";

        String origins="origins=800+Alma+Street+Menlo+Park+CA+94025";
        String destinations="destinations=585+Franklin+St+Mountain+View+CA+94041";
        String units="units=imperial";

        String key=System.getenv("GOOGLE_API_KEY");
        if (key == null) {
            throw new Exception("GOOGLE_API_KEY environment variable not set");
        }
        key = "key=" + key;

        path += origins + "&" + destinations + "&" + units + "&" + key;

        CloseableHttpClient httpClient = HttpClients.custom().build();
        String json = Http.invoke(httpClient, HttpMethod.GET, googleApiServer, path);
        System.out.println(json);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
