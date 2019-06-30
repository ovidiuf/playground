package playground.consistent.hashing.impl;

import playground.consistent.hashing.ClusterAddress;
import playground.consistent.hashing.NodeAddress;
import playground.consistent.hashing.impl.clustermanager.ClusterManager;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class SameAddressSpaceNodeAddress implements NodeAddress {

    @Override
    public String toString() {

        return "node://..." ;
    }
}
