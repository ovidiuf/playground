package playground.consistent.hashing;

import playground.consistent.hashing.impl.clustermanager.ClusterView;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public interface Client<K, V> {

    /**
     * Connect this client to a cluster.
     */
    void connect(ClusterAddress ca);

    /**
     * @exception RuntimeException on underlying failure
     */
    void write(K key, V value);

    /**
     * @return the corresponding value, or null if no such key
     *
     * @exception RuntimeException on underlying failure
     */
    V read(K key);

    /**
     * @return the view of the cluster the client currently has.
     */
    ClusterView<K> getClusterView();
}
