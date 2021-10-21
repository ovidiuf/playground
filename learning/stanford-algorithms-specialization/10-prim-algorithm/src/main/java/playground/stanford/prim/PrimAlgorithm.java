package playground.stanford.prim;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("unused")
public class PrimAlgorithm {

    // true means the corresponding vertex is in X, false otherwise
    private boolean[] X;
    private int xSize = 0;

    private Set<Edge> T;
    public long spanningTreeCost = 0;

    public void run(Graph g) {
        int vertexCount = g.size();
        // X is the set of vertices that we spanned so far
        X = new boolean[vertexCount];
        for (int i = 0; i < vertexCount; i ++) {
            X[i] = false;
        }
        // s ∈ V chosen arbitrarily
        int s = new Random().nextInt(g.vertexCount);
        addVertexToX(s);
        // T is the minimum spanning tree built so far. Invariant: X = vertices spanned by the three-so-far T
        T = new HashSet<>();
        System.out.print("remaining nodes: ");
        while(xSize < vertexCount) {
            System.out.print((vertexCount - xSize) + " ");
            // non-optimized implementation: scan all edges and find the cheapest edge (u,v) with u ∈ X, v ∉ X
            int minCost = Integer.MAX_VALUE;
            Edge cheapestEdge = null;
            for(Iterator<Edge> i = g.edgeIterator(); i.hasNext(); ) {
                Edge e = i.next();
                if (!X[e.v1]) {
                    // u not in X
                    continue;
                }
                if (X[e.v2]) {
                    // v already in X
                    continue;
                }
                // u ∈ X, v ∉ X
                if (e.cost < minCost) {
                    minCost = e.cost;
                    cheapestEdge = e;
                }
            }
            assert cheapestEdge != null;
            T.add(cheapestEdge);
            spanningTreeCost += cheapestEdge.cost;
            addVertexToX(cheapestEdge.v2);
        }
        System.out.println();
    }

    private void addVertexToX(int v) {
        assert !X[v];
        X[v] = true;
        xSize ++;
    }

    public Set<Edge> getT() {
        return T;
    }
}
