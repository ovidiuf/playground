package playground.stanford.clustering;

/**
 * The union find also gives access to the underlying graph
 */
public class UnionFind {

    private final Graph g;
    // the size of the cluster for the clusters that exist, -1 for clusters that disappeared.
    private final int[] clusterSizes;

    /**
     * Initializes each node of the graph to be part of its own component (cluster).
     */
    public UnionFind(Graph g) {
        this.clusterSizes = new int[g.size()];
        int i = 0;
        for(Vertex v: g.adj) {
            v.leader = v.label;
            clusterSizes[i++] = 1;
        }
        this.g = g;

    }

    /**
     * @return the cluster of the given vertex, which is the cluster leader's label.
     */
    public int find(int v) {
        return g.getVertex(v).leader;
    }

    /**
     * @return the index of the vertex that becomes the new leader
     */
    public int union(int clusterLeader1, int clusterLeader2) {
        if (clusterLeader1 < 0 || clusterLeader1 >= g.size() || clusterSizes[clusterLeader1] == -1) {
            throw new IllegalArgumentException("cluster " + clusterLeader1 + " does not exist");
        }
        if (clusterLeader2 < 0 || clusterLeader2 >= g.size() || clusterSizes[clusterLeader2] == -1) {
            throw new IllegalArgumentException("cluster " + clusterLeader2 + " does not exist");
        }
        int leaderThatDisappears = clusterLeader1, leaderThatRemains = clusterLeader2;
        // update the leader of the smallest cluster, to get a global log n running time
        if (clusterSize(clusterLeader1) > clusterSize(clusterLeader2)) {
            // update the leader of cluster 2
            leaderThatDisappears = clusterLeader2;
            leaderThatRemains = clusterLeader1;
        }
        for(Vertex v: g.adj) {
            if (v.leader == leaderThatDisappears) {
                v.leader = leaderThatRemains;
                clusterSizes[leaderThatRemains] += 1;
            }
        }
        clusterSizes[leaderThatDisappears] = -1;
        return leaderThatRemains;
    }

    /**
     * @param clusterLeader 0-based index of the cluster leader
     * @return the size of the cluster, or -1 if the cluster does not exist.
     */
    public int clusterSize(int clusterLeader) {
        return clusterSizes[clusterLeader];
    }

    public int getClusterCount() {
        int c = 0;
        for (int clusterSize : clusterSizes) {
            if (clusterSize == -1) {
                continue;
            }
            c ++;
        }
        return c;
    }

    public Graph getGraph() {
        return g;
    }
}
