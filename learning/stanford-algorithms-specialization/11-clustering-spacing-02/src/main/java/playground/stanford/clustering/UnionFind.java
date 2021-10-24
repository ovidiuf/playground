package playground.stanford.clustering;

/**
 * The union find also gives access to the underlying graph
 */
public class UnionFind {

    private final PointSet ps;
    // the size of the cluster for the clusters that exist, -1 for clusters that disappeared.
    private final int[] clusterSizes;

    /**
     * Initializes each node of the graph to be part of its own component (cluster).
     */
    public UnionFind(PointSet ps) {
        this.clusterSizes = new int[ps.size];
        int i = 0;
        for(Point p: ps.points) {
            p.leader = p.id;
            clusterSizes[i++] = 1;
        }
        this.ps = ps;

    }

    /**
     * @return the cluster of the given ID, which is the cluster leader's ID.
     */
    public int find(int id) {
        Point p = ps.getPoint(id);
        assert p.id == id;
        return p.leader;
    }

    /**
     * @return the index of the vertex that becomes the new leader
     */
    public int union(int clusterLeader1, int clusterLeader2) {
        if (clusterLeader1 < 0 || clusterLeader1 >= ps.size || clusterSizes[clusterLeader1] == -1) {
            throw new IllegalArgumentException("cluster " + clusterLeader1 + " does not exist");
        }
        if (clusterLeader2 < 0 || clusterLeader2 >= ps.size || clusterSizes[clusterLeader2] == -1) {
            throw new IllegalArgumentException("cluster " + clusterLeader2 + " does not exist");
        }
        if (clusterLeader1 == clusterLeader2) {
            // already merged
            return clusterLeader1;
        }
        int leaderThatRemains = clusterLeader1, leaderThatDisappears = clusterLeader2;
        // update the leader of the smallest cluster, to get a global log n running time
        if (clusterSize(clusterLeader2) > clusterSize(clusterLeader1)) {
            // update the leader of cluster 2
            leaderThatDisappears = clusterLeader1;
            leaderThatRemains = clusterLeader2;
        }
        for(Point p : ps.points) {
            if (p.leader == leaderThatDisappears) {
                p.leader = leaderThatRemains;
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

    public PointSet getGraph() {
        return ps;
    }
}
