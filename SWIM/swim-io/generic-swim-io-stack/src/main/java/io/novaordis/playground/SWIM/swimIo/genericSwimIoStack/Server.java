package io.novaordis.playground.SWIM.swimIo.genericSwimIoStack;

import swim.server.SwimPlane;
import swim.server.SwimServer;

public class Server {

    public static void main(String[] args) {

        SwimServer swimServer = new SwimServer();

        SwimPlane plane = swimServer.materializePlane("test-plane", TestPlane.class);

        plane.bind("localhost", 9999);

        System.out.println("'test-plane' plane bound to localhost:9999");

        swimServer.run();

        System.out.println("SWIM server running ...");
    }
}
