package playground.stanford.dijkstra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NonOptimizedDijkstra {

    private final Set<Integer> X;
    private int[] A;

    public NonOptimizedDijkstra() {
        this.X = new HashSet<>();
    }

    public int getShortestPathTo(int v) {
        return A[v];
    }

    public void run(Graph g, int sourceVertex) {
        A = new int[g.size()];
        X.clear();
        X.add(sourceVertex);
        A[sourceVertex] = 0;

        // while there are still nodes to process
        while(X.size() < g.size()) {
            int minDijkstraScore = Integer.MAX_VALUE;
            Edge vw = null;
            //
            // for every edge (v, w) that crosses X/V-X boundary, compute the Dijkstra greedy score and pick the minimum
            //
            List<Edge> edgs = g.getEdgesThatCrossTheBoundary(X);
            for(Edge e: edgs) {
                assert X.contains(e.v1);
                int dijkstraGreedyScore = A[e.v1] + e.length;
                if (dijkstraGreedyScore < minDijkstraScore) {
                    minDijkstraScore = dijkstraGreedyScore;
                    vw = e;
                }
            }
            assert vw != null;
            // add the chosen vertex to X
            X.add((vw.v2));
            A[vw.v2] = minDijkstraScore;
        }
    }
}
