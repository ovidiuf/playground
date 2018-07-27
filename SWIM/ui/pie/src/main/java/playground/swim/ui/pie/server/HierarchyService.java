package playground.swim.ui.pie.server;

import recon.Value;
import swim.api.AbstractService;
import swim.api.SwimLane;
import swim.api.ValueLane;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
class HierarchyService extends AbstractService {

    @SwimLane("test-lane-0")
    private final ValueLane testLane0 = valueLane().didSet((Value old, Value news) -> {

        System.out.println("test-lane-0: " + old + " -> " + news);
    });

    @SwimLane("test-lane-1")
    private final ValueLane testLane1 = valueLane().didSet((Value old, Value news) -> {

        System.out.println("test-lane-1: " + old + " -> " + news);
    });
}
