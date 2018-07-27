package playground.swim.ui.pie.server;

import swim.server.SwimPlane;
import swim.server.SwimServer;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class Main {

    public static void main(String[] args) {

        String planeName = "pie-plane";
        String address = "localhost";
        int port = 9031;

        SwimServer swimServer = new SwimServer();

        SwimPlane plane = swimServer.materializePlane(planeName, PiePlane.class);

        plane.bind(address, port);

        swimServer.run();

        System.out.println("SWIM server running, \"" + planeName + "\" bound to " + address + ":" + port + " ...");
    }
}
