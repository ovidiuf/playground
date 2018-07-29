package io.novaordis.playground.SWIM.service.server;

import swim.api.AbstractPlane;
import swim.api.ServiceType;
import swim.api.SwimRoute;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/28/18
 */
public class SimplePlane extends AbstractPlane {

    @SwimRoute("/simple-service/:id")
    private final ServiceType<?> root = serviceClass(SimpleService.class);

}
