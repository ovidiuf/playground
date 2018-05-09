package io.novaordis.playground.swim.simplest;

import swim.server.SwimPlane;
import swim.server.SwimServer;

public class Server {

    public static final String PLANE_INTERFACE = "localhost";
    public static final int PLANE_PORT = 9009;
    public static final String PLANE_NAME = "devices";

    public static void main(String[] args) {

        final SwimServer server = new SwimServer();

        SwimPlane plane = server.materializePlane("devices", Devices.class);

        plane.bind(PLANE_INTERFACE, PLANE_PORT);

        server.run();

        System.out.println("plane \"" + PLANE_NAME + "\" listening on " + PLANE_INTERFACE + ":" + PLANE_PORT);
    }
}
