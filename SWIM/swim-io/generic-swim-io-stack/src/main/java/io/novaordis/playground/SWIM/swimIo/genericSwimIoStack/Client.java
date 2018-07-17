package io.novaordis.playground.SWIM.swimIo.genericSwimIoStack;

import recon.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import swim.client.SwimClient;

public class Client {

    public static void main(String[] args) throws Exception {

        final SwimClient client = new SwimClient();

        client.start();

        System.out.println("client ready to send commands ...");

        sendInALoop(client);
    }

    private static void sendInALoop(final SwimClient client) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean active = true;

        while(active) {

            System.out.print("> ");

            br.readLine();

            client.command("ws://localhost:9999", "test-service/1", "commands", Value.of("test-command"));

            System.out.println("command sent");
        }

    }
}
