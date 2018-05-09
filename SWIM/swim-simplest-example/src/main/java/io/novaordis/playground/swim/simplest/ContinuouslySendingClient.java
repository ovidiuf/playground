package io.novaordis.playground.swim.simplest;

import recon.Value;
import swim.api.ValueDownlink;
import swim.client.SwimClient;

import java.io.BufferedReader;
import java.io.IOException;
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


        stdinLoop(client, link);

    }

    private static void stdinLoop(SwimClient client,  ValueDownlink<Value> link) throws IOException  {

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

            processAndSendContent(link, line);
        }
    }

    private static void processAndSendContent(ValueDownlink<Value> link, String line) {

        Object v;

        try {

            v = Integer.parseInt(line);

            // sending as Integer
        }
        catch(Exception e) {

            // that's fine, sending as String

            v = line;
        }

        link.set(Value.of(v));
    }

}
