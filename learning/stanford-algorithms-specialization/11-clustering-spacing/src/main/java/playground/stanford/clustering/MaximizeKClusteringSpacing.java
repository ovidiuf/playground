package playground.stanford.clustering;

import java.util.Collections;
import java.util.List;

public class MaximizeKClusteringSpacing {
    /**
     * @param k the target cluster count
     * @param u the union find that contains the graph with edge costs
     * @return the maximum spacing, which is immediately bigger cost of the edge after the k clusters form
     */
    public int run(int k, UnionFind u) {
        int n = u.getGraph().size();
        // we start with n clusters
        assert n == u.getClusterCount();
        // sort the edges by cost, from smallest to largest
        List<Edge> sortedEdged = u.getGraph().getDistinctEdges();
        assert n * (n - 1) / 2 == sortedEdged.size();
        Collections.sort(sortedEdged);
        // walk the list of edges in the increasing order of their cost and for all those edges that span two distinct
        // clusters, merge the clusters until we reach the target cluster count k
        for (Edge e : sortedEdged) {
            int cluster1 = u.find(e.v1);
            int cluster2 = u.find(e.v2);
            if (cluster1 != cluster2) {
                if (u.getClusterCount() == k) {
                    // we already reached the target cluster count k, stop and return the spacing between the clusters
                    // which is the minimum distance between two points in separated clusters
                    return e.cost;
                }
                else {
                    // merge the clusters
                    u.union(cluster1, cluster2);
                }
            }
        }
        throw new IllegalStateException("we should not have gotten here");
    }
}
