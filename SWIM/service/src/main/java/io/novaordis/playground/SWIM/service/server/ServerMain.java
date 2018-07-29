package io.novaordis.playground.SWIM.service.server;

import swim.server.SwimPlane;
import swim.server.SwimServer;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/28/18
 */
public class ServerMain {

    public static final int DEFAULT_PORT = 9023;

    public static void main(String[] args) {

        SwimServer server = new SwimServer();

        SwimPlane plane = server.materializePlane("simple-plane", SimplePlane.class);

        plane.bind("localhost", DEFAULT_PORT);

        server.run();

        System.out.println("SWIM server up on localhost:" + DEFAULT_PORT);
    }
}
