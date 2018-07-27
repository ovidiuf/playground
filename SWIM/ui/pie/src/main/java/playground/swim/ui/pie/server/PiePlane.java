package playground.swim.ui.pie.server;

import swim.api.AbstractPlane;
import swim.api.ServiceType;
import swim.api.SwimRoute;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class PiePlane extends AbstractPlane {

    @SwimRoute("/pie-service/:id")
    private final ServiceType<?> s = serviceClass(PieService.class);

}
