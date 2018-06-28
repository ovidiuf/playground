package io.novaordis.playground.SWIM.swimIo.genericSwimIoStack;

import recon.Value;

import swim.api.AbstractService;
import swim.api.CommandLane;
import swim.api.SwimLane;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 6/28/18
 */
public class TestService extends AbstractService {

    @SwimLane("commands")
    private final CommandLane commandLane;

    private TestService() {

        commandLane = commandLane().didCommand((Value command) -> {

            String c = command.stringValue();

            System.out.println(nodeUri().toUri() + " received command " + c);
        });
    }

}
