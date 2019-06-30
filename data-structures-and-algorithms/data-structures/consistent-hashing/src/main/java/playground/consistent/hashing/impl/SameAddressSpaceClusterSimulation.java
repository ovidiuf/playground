package playground.consistent.hashing.impl;


import playground.consistent.hashing.Client;
import playground.consistent.hashing.ClusterAddress;
import playground.consistent.hashing.impl.client.SameAddressSpaceClient;
import playground.consistent.hashing.impl.clustermanager.ClusterManager;

/**
 * A simulation of a consistent hashing cluster in the same address space.
 *
 * Includes a Client, Nodes, and simulated network communication between Client and Nodes and among Nodes.
 *
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
public class SameAddressSpaceClusterSimulation<K, V> {

    private SameAddressSpaceClient<K, V> client;

    private ClusterManager clusterManager;

    private SameAddressSpaceClusterSimulation() {

        System.out.println("constructing cluster simulation ...");

        clusterManager = new ClusterManager();

        //noinspection unchecked
        client = new SameAddressSpaceClient();

        System.out.println("connecting " + client + " to " + clusterManager + " ...");

        ClusterAddress clusterAddress = clusterManager.getClusterAddress();

        client.connect(clusterAddress);

        System.out.println(this + " initialized");
    }

    Client<K, V> getClient() {

        return client;
    }

    String getInfo() {

        return
                client.getInfo() + "\n" +
                clusterManager.getInfo();
    }

    public static void main(String[] args) throws Exception {

        System.out.println("initializing cluster simulation ...");

        SameAddressSpaceClusterSimulation simulation = new SameAddressSpaceClusterSimulation();

        CommandLineLoop c = new CommandLineLoop(simulation);

        c.run();
    }
}
