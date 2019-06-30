package playground.consistent.hashing.impl;

import playground.consistent.hashing.ClusterAddress;
import playground.consistent.hashing.impl.clustermanager.ClusterManager;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class SameAddressSpaceReferenceClusterAddress implements ClusterAddress {

    private ClusterManager clusterManager;

    public SameAddressSpaceReferenceClusterAddress(ClusterManager clusterManager) {

        this.clusterManager = clusterManager;
    }

    public ClusterManager getClusterManager() {

        return clusterManager;
    }

    @Override
    public String toString() {

        return "cluster://" + Integer.toHexString(System.identityHashCode(clusterManager));
    }
}
