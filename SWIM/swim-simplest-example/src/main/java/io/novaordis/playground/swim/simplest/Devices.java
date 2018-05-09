package io.novaordis.playground.swim.simplest;

import swim.api.AbstractPlane;
import swim.api.ServiceType;
import swim.api.SwimRoute;

/**
 * All devices of type A.
 */
public class Devices extends AbstractPlane {

    @SwimRoute("/a/:id")
    final ServiceType<?> a = serviceClass(io.novaordis.playground.swim.simplest.A.class);

}
