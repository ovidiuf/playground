package io.novaordis.playground.SWIM.service.server;

import recon.Value;

import swim.api.AbstractService;
import swim.api.CommandLane;
import swim.api.SwimLane;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/28/18
 */
public class SimpleService extends AbstractService {

    @SwimLane("command")
    private CommandLane<Value> command = commandLane().didCommand((Value c) -> {

        System.out.println(nodeUri() + " got " + c.stringValue());
    });
}
