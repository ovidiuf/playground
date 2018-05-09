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

        HostRef host = client.hostRef("ws://localhost:9009");
        NodeRef node = host.nodeRef("a/device1");
        LaneRef lane = node.laneRef("metric");
        ValueDownlink<Value> link = lane.downlinkValue();

        link.keepSynced(true);

        link.open();

        link.didReceive(value -> System.out.println("received " + value + ": " + value.toRecon()));

        link.didSet((newValue, oldValue) -> {

            System.out.println("set new: " + newValue + " (" + newValue.toRecon() + ")" + ", old: " + oldValue + " (" + oldValue.toRecon() + ")" );
        });

    }

}
