package playground.swim.maplane.client;

import playground.swim.maplane.server.PlaneExample;
import playground.swim.maplane.server.ServiceExample;

import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class Main {

    private static final String HOST_URI = "ws://localhost:9000";

    public static void main(String[] args) throws Exception {

        CommandLineClient c =
                new CommandLineClient(HOST_URI, PlaneExample.SERVICE_TYPE_NAME, ServiceExample.MAP_LANE_NAME);

        c.run();

        System.exit(0);
    }
}
