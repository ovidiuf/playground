package playground.consistent.hashing.impl.client;

import playground.consistent.hashing.Client;
import playground.consistent.hashing.ClusterAddress;
import playground.consistent.hashing.NodeAddress;
import playground.consistent.hashing.impl.UserErrorException;
import playground.consistent.hashing.impl.clustermanager.ClusterManager;
import playground.consistent.hashing.impl.SameAddressSpaceReferenceClusterAddress;
import playground.consistent.hashing.impl.clustermanager.ClusterView;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class SameAddressSpaceClient<K, V> implements Client<K, V> {

    private ClusterAddress clusterAddress;

    private ClusterView<K> cachedClusterView;

    @Override
    public void connect(ClusterAddress ca) {

        this.clusterAddress = ca;

        updateClusterView();

        System.out.println(this + " has connected to " + ca);
    }

    @Override
    public void write(K key, V value) {

        insureLocalViewIsConsistent();

        //
        // look up the node to write to
        //

        NodeAddress na = cachedClusterView.getNodeForKey(key);

        if (na == null) {

            throw new IllegalStateException("no node to handle the key exists");
        }

        throw new RuntimeException("NOT YET IMPLEMENTED: write() -> locating the node to write to");

        //System.out.println("wrote " + key + "=" + value + " to node ?");
    }

    @Override
    public V read(K key) {

        insureLocalViewIsConsistent();

        throw new RuntimeException("NOT YET IMPLEMENTED");
    }

    @Override
    public ClusterView<K> getClusterView() {

        return cachedClusterView;
    }

    @Override
    public String toString() {

        return "client[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    public String getInfo() {

        return this + " connected to " + clusterAddress;
    }

    private void insureLocalViewIsConsistent() {

        ClusterManager clusterManager = ((SameAddressSpaceReferenceClusterAddress)clusterAddress).getClusterManager();

        if (clusterManager.getClusterVersion() == cachedClusterView.getClusterVersion()) {

            //
            // local view is consistent
            //

            System.out.println("local cluster view (version " + cachedClusterView.getClusterVersion() + ") is consistent");

            return;
        }

        //
        // not consistent, update
        //

        updateClusterView();
    }

    /**
     * The client maintains a cluster "view", which includes the cluster version, hash wheel sections associated with
     * each node, etc.
     *
     * It is the client's responsibility to periodically check that its "view" corresponds to the actual situation
     * on the ground, so read and write requests are sent to the correct nodes.
     */
    private void updateClusterView() {

        ClusterManager clusterManager = ((SameAddressSpaceReferenceClusterAddress)clusterAddress).getClusterManager();

        //
        // this must be atomic so the cluster is not modified while we're updating the view
        //

        this.cachedClusterView = clusterManager.getClusterView();

        System.out.println(this + " updated cluster view to version " + cachedClusterView.getClusterVersion());
    }
}
