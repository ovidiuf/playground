package io.novaordis.playground.swim.simplest;

import recon.Value;
import swim.api.ValueDownlink;
import swim.client.SwimClient;

public class SendingClient {

    public static void main(String[] args) throws Exception {

        SwimClient client = new SwimClient();

        client.start();

        client.
                hostRef("ws://localhost:9009").
                nodeRef("a/device1").
                laneRef("metric").
                downlinkValue().
                keepSynced(true).
                open().
                set(Value.of("something"));

//        downlink.
//                keepSynced(true).
//                open().didSet((newValue, oldValue) -> {
//
//                    System.out.println("new value: " + newValue + ", old value: " + oldValue);
//
//                    downlink.close();
//                    client.stop();
//
//                }).
//                set(Value.of("blah"));


//        final ValueDownlink<Value> downlink = client.
//                hostRef("ws://localhost:9009").
//                nodeRef("a/dev1").
//                laneRef("metric").
//                downlinkValue();
//
//        downlink.
//                keepSynced(true).
//                open().didSet((newValue, oldValue) -> {
//
//                    System.out.println("new value: " + newValue + ", old value: " + oldValue);
//
//
//                }).
//                set(Value.of("something"));


//        Thread.sleep(10000L);

    }

}
