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
    private final MapLane mapLane =
            mapLane().
                    didUpdate((Value key, Value newValue, Value oldValue) -> {

                        //
                        // we assume that both the key and the values are Strings
                        //

                        System.out.println(key.stringValue() + ": " + oldValue.stringValue() + " replaced by " + newValue.stringValue());

            });


}
