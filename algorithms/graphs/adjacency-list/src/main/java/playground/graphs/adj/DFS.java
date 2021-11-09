package playground.graphs.adj;

import java.util.Arrays;

@SuppressWarnings("DuplicatedCode")
public class DFS {
    /**
     * Searches the graph, marks nodes as visited.
     */
    public void dfs(G g, int startVertexIndex) {
        V s = g.vertex(startVertexIndex);
        s.seen = true; // mark s as explored
        for(E e: s.edges) { // for every edge (s, v)
            V v = e.getVertexOppositeTo(s);
            if (!v.seen) { // if v is unexplored
                dfs(g, v.id); // depth first search recursively
            }
        }
    }
}
