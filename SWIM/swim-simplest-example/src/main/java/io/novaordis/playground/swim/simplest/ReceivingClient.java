package io.novaordis.playground.swim.simplest;

import recon.Value;
import swim.api.HostRef;
import swim.api.LaneRef;
import swim.api.NodeRef;
import swim.api.ValueDownlink;
import swim.client.SwimClient;

public class ReceivingClient {

    public static void main(String[] args) {

        final SwimClient client = new SwimClient();

        client.start();

        //
        // link to a service instance
        //

        ValueDownlink<Value> link = client.
                hostRef("ws://localhost:9009").
                nodeRef("a/device1").
                laneRef("metric").
                downlinkValue().
                keepSynced(true).
                open();

        link.didReceive(
                (Value v) -> System.out.println("received " + v + ": " + v.toRecon()));

        link.didSet((Value newValue, Value oldValue) -> {

            System.out.println("set new: " + newValue + " (" + newValue.toRecon() + ")" + ", old: " + oldValue + " (" + oldValue.toRecon() + ")" );
        });

    }

}
