package playground.swim.maplane.client;

import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class Main {

    private static final String HOST_URI = "ws://localhost:9000";
    private static final String SERVICE_NAME = "service-example";
    private static final String MAP_LANE_NAME = "map-lane-example";

    public static void main(String[] args) throws Exception {

        CommandLineClient c = new CommandLineClient(HOST_URI, SERVICE_NAME, MAP_LANE_NAME);

        c.run();

        System.exit(0);
    }
}
