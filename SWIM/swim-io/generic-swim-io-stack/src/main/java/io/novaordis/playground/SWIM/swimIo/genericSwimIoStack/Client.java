package io.novaordis.playground.SWIM.swimIo.genericSwimIoStack;

import recon.Value;

import swim.client.SwimClient;

public class Client {

    public static void main(String[] args) throws Exception {

        SwimClient client = new SwimClient();

        client.start();

        client.command("ws://localhost:9999", "test-service/1", "commands", Value.of("test-command"));

        System.out.println("command sent");
    }
}
