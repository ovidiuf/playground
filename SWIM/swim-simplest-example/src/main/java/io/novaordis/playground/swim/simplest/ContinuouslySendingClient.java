package io.novaordis.playground.swim.simplest;

import recon.Value;
import swim.api.ValueDownlink;
import swim.client.SwimClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ContinuouslySendingClient {

    public static void main(String[] args) throws Exception {

        final SwimClient client = new SwimClient();

        client.start();

        ValueDownlink<Value> link = client.
                hostRef("ws://localhost:9009").
                nodeRef("a/device1").
                laneRef("metric").
                downlinkValue().
                keepSynced(true).
                open();


        //
        // stdin loop
        //

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            System.out.print("> ");

            String line = br.readLine();

            if (line.trim().isEmpty()) {

                continue;
            }

            if ("exit".equalsIgnoreCase(line)) {

                client.stop();
                client.close();

                System.out.println("exiting ...");

                break;
            }

            link.set(Value.of(line));
        }
    }

}
