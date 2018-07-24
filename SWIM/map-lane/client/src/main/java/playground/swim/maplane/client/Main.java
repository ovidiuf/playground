package playground.swim.maplane.client;

import recon.Form;
import recon.Value;

import swim.api.MapDownlink;
import swim.client.SwimClient;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String hostUri = "ws://localhost:9000";
        String nodeUri = "service-example/1";
        String laneUri = "map";

        final SwimClient swimClient = new SwimClient();

        swimClient.start();

        final MapDownlink<Long, Value> link = swimClient.
                downlinkMap().
                keyForm(Form.LONG).
                hostUri(hostUri).
                nodeUri(nodeUri).
                laneUri(laneUri).
                open();

        link.didLink(() -> {

            System.out.println("linked");

            link.put(1L, Value.of("blah"));
        });
    }
}
