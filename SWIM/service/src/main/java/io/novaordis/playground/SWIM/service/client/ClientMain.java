package io.novaordis.playground.SWIM.service.client;

import recon.Value;

import io.novaordis.playground.SWIM.service.server.ServerMain;

import swim.client.SwimClient;
import swim.util.Uri;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/28/18
 */
public class ClientMain {

    public static void main(String[] args) {

        SwimClient client = new SwimClient();

        client.start();

        client.command(
                Uri.parse("ws://localhost:" + ServerMain.DEFAULT_PORT),
                Uri.parse("simple-service/1"),
                Uri.parse("command"),
                Value.of("something"));

        System.out.println("command sent");
    }
}
