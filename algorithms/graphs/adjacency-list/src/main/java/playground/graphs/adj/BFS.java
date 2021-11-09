package playground.graphs.adj;

import java.util.Arrays;

public class BFS {
    /**
     * Searches the graph, marks nodes as visited.
     */
    public void bfs(G g, int startVertexIndex) {
        Arrays.stream(g.adj).forEach(v -> v.seen = false); // mark all graph vertices as not "seen"
        Q<V> q = new Q<>(g.size());  // initialize a queue
        V s = g.vertex(startVertexIndex);
        s.seen = true; // mark s as explored
        q.enqueue(s); // places s in the queue
        while(!q.isEmpty()) { // while the queue has elements, remove the head of the queue
            V v = q.dequeue(); // remove the head of the queue
            for(E e: v.edges) { // for each edge (v, w):
                V w = e.getVertexOppositeTo(v);
                if (!w.seen) { // if w is not explored
                    w.seen = true; // mark w as explored
                    q.enqueue(w); // add w to Q
                }
            }
        }
    }
}
