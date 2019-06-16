package playground.consistent.hashing.impl.clustermanager;

import playground.consistent.hashing.ClusterAddress;
import playground.consistent.hashing.Node;
import playground.consistent.hashing.impl.SameAddressSpaceReferenceClusterAddress;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages Nodes (new additions, removals and failures).
 *
 * Clients get information about Nodes and access to Nodes via the ClusterManager.
 *
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class ClusterManager {

    private int clusterVersion;

    // a reference to the external

    private Set<Node> nodes;

    public ClusterManager() {

        this.nodes = new HashSet<>();
        this.clusterVersion = 0;
    }

    @Override
    public String toString() {

        return "cluster-manager[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    public ClusterAddress getClusterAddress() {

        return new SameAddressSpaceReferenceClusterAddress(this);
    }

    public String getInfo() {

        return
                "cluster manager:\n" +
                "    cluster version: " + clusterVersion + "\n" +
                "    nodes: " + nodes;
    }

    public int getClusterVersion() {

        return clusterVersion;
    }

    /**
     * This must be atomic to make sure the cluster is not updated while we're running this method.
     */
    public ClusterView getClusterView() {

        ClusterView cv = new ClusterView();
        cv.setClusterVersion(clusterVersion);

        return cv;
    }
}
