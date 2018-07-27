package playground.swim.ui.pie.server;

import recon.Value;
import swim.api.AbstractService;
import swim.api.SwimLane;
import swim.api.ValueLane;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
class PieService extends AbstractService {

    @SwimLane("pie-value-lane")
    private final ValueLane pieValueLane = valueLane().didSet((Value old, Value news) -> {

        System.out.println("pie-value-lane: " + old + " -> " + news);
    });
}
