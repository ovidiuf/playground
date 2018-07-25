package playground.swim.maplane.server;

import swim.api.AbstractPlane;
import swim.api.ServiceType;
import swim.api.SwimRoute;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class PlaneExample extends AbstractPlane {

    public static final String SERVICE_TYPE_NAME = "service-example";

    @SwimRoute("/" + SERVICE_TYPE_NAME + "/:id")
    private final ServiceType<?> s = serviceClass(ServiceExample.class);

}
