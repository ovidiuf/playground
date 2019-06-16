package playground.consistent.hashing.impl.clustermanager;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class ClusterView {

    private int clusterVersion;

    public int getClusterVersion() {

        return clusterVersion;
    }

    void setClusterVersion(int clusterVersion) {

        this.clusterVersion = clusterVersion;
    }
}
