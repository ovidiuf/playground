package playground.consistent.hashing.impl.client;

import playground.consistent.hashing.Client;
import playground.consistent.hashing.ClusterAddress;
import playground.consistent.hashing.impl.clustermanager.ClusterManager;
import playground.consistent.hashing.impl.SameAddressSpaceReferenceClusterAddress;
import playground.consistent.hashing.impl.clustermanager.ClusterView;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class SameAddressSpaceClient<K, V> implements Client<K, V> {

    private ClusterManager clusterManager;

    private int cachedClusterVersion;

    @Override
    public void connect(ClusterAddress ca) {

        this.clusterManager = ((SameAddressSpaceReferenceClusterAddress)ca).getClusterManager();

        updateClusterView();

        System.out.println(this + " has connected to " + clusterManager);
    }

    @Override
    public void write(K key, V value) {

        insureLocalViewIsConsistent();

        System.out.println("wrote " + key + "=" + value + " to node ?");
    }

    @Override
    public V read(K key) {

        insureLocalViewIsConsistent();

        throw new RuntimeException("NOT YET IMPLEMENTED");
    }

    @Override
    public String toString() {

        return "client[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    public String getInfo() {

        return this + " connected to " + clusterManager;
    }

    private void insureLocalViewIsConsistent() {

        if (clusterManager.getClusterVersion() == cachedClusterVersion) {

            //
            // local view is consistent
            //

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

        //
        // this must be atomic so the cluster is not modified while we're updating the view
        //

        ClusterView v = clusterManager.getClusterView();

        this.cachedClusterVersion = v.getClusterVersion();

        System.out.println(this + " updated cluster view");
    }
}
