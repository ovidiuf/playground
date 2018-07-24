package playground.swim.maplane.server;

import recon.Form;
import recon.Value;
import swim.api.AbstractService;
import swim.api.MapLane;
import swim.api.SwimLane;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
class ServiceExample extends AbstractService {

    @SwimLane("map-lane-example")
    private final MapLane<Long, Value> map =
            mapLane().
                    keyForm(Form.LONG).
                    didUpdate((Long key, Value newValue, Value oldValue) -> {

                        System.out.println(key + ": " + oldValue + " -> " + newValue);

            });


}
