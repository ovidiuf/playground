package io.novaordis.playground.SWIM.swimIo.genericSwimIoStack;

import swim.api.AbstractPlane;
import swim.api.ServiceType;
import swim.api.SwimRoute;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 6/28/18
 */
public class TestPlane extends AbstractPlane {

    @SwimRoute("/test-service/:id")
    private final ServiceType<?> testServiceType = serviceClass(TestService.class);

}
