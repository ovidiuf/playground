/*
 * Copyright (c) 2018 Nova Ordis LLC
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

package io.novaordis.playground.spdy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/18/18
 *
 * http://square.github.io/okhttp/
 *
 * https://github.com/2013techsmarts/SmartTechie_Examples_Repo/blob/master/okHTTP-Sample/src/main/java/org/smarttechie/HttpPOSTExample.java
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        //Create the media type which are going to post
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "");

        OkHttpClient client = new OkHttpClient();

        System.out.println("client created: " + client);

        Request request = new Request.Builder()
                .url("https://master.openshift.novaordis.io:443/api/v1/namespaces/test/pods/centos-loop/exec?command=%2Fopt%2Fprobe&container=centos-loop&container=centos-loop&stderr=true&stdout=true")
                .post(body)
                .addHeader("X-Stream-Protocol-Version", "v4.channel.k8s.io")
                .addHeader("X-Stream-Protocol-Version", "v3.channel.k8s.io")
                .addHeader("X-Stream-Protocol-Version", "v2.channel.k8s.io")
                .addHeader("Connection", "Upgrade")
                .addHeader("Upgrade", "SPDY/3.1")
                .addHeader("Authorization", "Bearer q9pTHqWeh3bc8YK7oA6Y-lvjWpJqW_MzKt4kM4luoQU")
                .build();
        System.out.println("request built: " + request);

        System.out.println("sending request ...");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                throw new RuntimeException("onFailure() NOT YET IMPLEMENTED");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {

                    System.out.println("The data is POSTED successfully to the END point");
                } else {

                    System.out.println("response code:    " + response.code());
                    System.out.println("response message: " + response.message());
                    ResponseBody rbody = response.body();
                    Headers headers = response.headers();
                    for (String name : headers.names()) {

                        System.out.println(name + ": " + headers.get(name));
                    }

                    InputStream is = rbody.byteStream();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    int b;
                    while ((b = is.read()) != -1) {

                        baos.write(b);
                    }

                    System.out.println(new String(baos.toByteArray()));
                }

            }
        });

        System.out.println("press a key");
        System.in.read();
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
