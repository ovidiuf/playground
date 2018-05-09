package io.novaordis.playground.swim.simplest;

import recon.Value;
import swim.client.SwimClient;

public class SynchronouslySendingClient {

    public static void main(String[] args) {

        final SwimClient client = new SwimClient();

        client.start();

        client.
                hostRef("ws://localhost:9009").
                nodeRef("a/device1").
                laneRef("metric").
                downlinkValue().
                keepSynced(true).
                open().
                didSet((newValue, oldValue) -> {

                    //
                    // we use this callback to close the client and exit after the first set on lane is
                    // *guaranteed* to have reached the server and came back; the client should stay up
                    // until then. Because other data may come over the line in the mean time, make sure
                    // to exit on the data *we* send
                    //

                    if (!Value.of("something").equals(newValue)) {

                        //
                        // uninteresting
                        //

                        System.out.println("got " + newValue + ", this is not our data, won't exit");

                        return;
                    }

                    System.out.println("got " + newValue + ", exiting ...");

                    client.stop();
                    client.close();

                }).
                set(Value.of("something"));

    }


}
