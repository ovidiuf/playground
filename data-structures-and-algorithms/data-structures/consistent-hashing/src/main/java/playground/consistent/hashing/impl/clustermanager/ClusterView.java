package playground.consistent.hashing.impl.clustermanager;

import playground.consistent.hashing.NodeAddress;

import java.util.Collections;
import java.util.List;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class ClusterView<K> {

    private int clusterVersion;

    public int getClusterVersion() {

        return clusterVersion;
    }

    public List<NodeAddress> getNodeAddresses() {

        return Collections.emptyList();
    }

    /**
     * Computes the key's hash and returns the address of the node that is supposed to handle the key.
     *
     * @return the NodeAddress of null if no node can be identified.
     */
    public NodeAddress getNodeForKey(K key) {

        return null;
    }

    void setClusterVersion(int clusterVersion) {

        this.clusterVersion = clusterVersion;
    }
}
