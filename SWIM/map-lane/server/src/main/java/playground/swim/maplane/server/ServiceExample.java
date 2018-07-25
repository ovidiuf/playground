package playground.swim.maplane.server;

import recon.Value;
import swim.api.AbstractService;
import swim.api.Identity;
import swim.api.MapLane;
import swim.api.SwimLane;
import swim.api.Uplink;
import swim.http.HttpRequest;
import swim.http.HttpResponse;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class ServiceExample extends AbstractService {

    public static final String MAP_LANE_NAME = "map-lane-example";

    @SwimLane(MAP_LANE_NAME)
    private final MapLane mapLane =
            mapLane().
                    didClear(() -> {
                        System.out.println(getMapLaneUri() + " cleared on thread " +
                                Thread.currentThread().getName());
                    }).
                    didCommand((Value body) -> {
                        System.out.println(getMapLaneUri() + " received command " + body + " on thread " +
                                Thread.currentThread().getName());
                    }).
                    didDrop((int lower) -> {
                        System.out.println(getMapLaneUri() + " dropped to " + lower + " on thread " +
                                Thread.currentThread().getName());
                    }).
                    didEnter((Identity i) -> {
                        System.out.println(getMapLaneUri() + " entered " + i + " on thread " +
                                Thread.currentThread().getName());
                    }).
                    didLeave((Identity i) -> {
                        System.out.println(getMapLaneUri() + " left " + i + " on thread " +
                                Thread.currentThread().getName());
                    }).
                    didRemove((Value key, Value oldValue) -> {
                        System.out.println(getMapLaneUri() + "'s key " + key.stringValue() +
                                " was removed, old value: " + oldValue.stringValue());
                    }).
                    didRequest((HttpRequest<Object> request) -> {

                        System.out.println(getMapLaneUri() + " got request " + request + " on thread " +
                                Thread.currentThread().getName());

                        return null;
                    }).
                    didRespond((HttpResponse<?> response) -> {
                        System.out.println(getMapLaneUri() + " got response " + response + " on thread " +
                                Thread.currentThread().getName());
                    }).
                    didTake((int upper) -> {
                        System.out.println(getMapLaneUri() + " took " + upper + " on thread " +
                                Thread.currentThread().getName());
                    }).
                    didUplink((Uplink uplink) -> {
                        System.out.println(getMapLaneUri() + " got uplink " + uplink + " on thread " +
                                Thread.currentThread().getName());
                    }).
                    didUpdate((Value key, Value newValue, Value oldValue) -> {

                        //
                        // we assume that both the key and the values are Strings
                        //

                        System.out.println(getMapLaneUri() + "'s key " + key.stringValue() + " was updated: " +
                                oldValue.stringValue() + " replaced by " + newValue.stringValue());

            });

    private String getServiceUri() {

        return this.context.nodeUri().getPath().toUri();
    }

    private String getMapLaneUri() {
        
        return getServiceUri() + "/" + MAP_LANE_NAME;
    }

}
