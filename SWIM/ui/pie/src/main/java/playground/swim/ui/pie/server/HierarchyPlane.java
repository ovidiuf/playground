package playground.swim.ui.pie.server;

import swim.api.AbstractPlane;
import swim.api.ServiceType;
import swim.api.SwimRoute;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class HierarchyPlane extends AbstractPlane {

    @SwimRoute("/test-hierarchy-service/:id")
    private final ServiceType<?> s = serviceClass(HierarchyService.class);

}
