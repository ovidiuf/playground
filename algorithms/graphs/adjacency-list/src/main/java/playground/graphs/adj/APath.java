package playground.graphs.adj;

public class APath {
    public static boolean foundPathWithDfs(G g, int sid, int tid) {
        V s = g.vertex(sid);
        s.seen = true; // mark v as visited
        for(E e: s.edges) { // for every edge (s, w)
            V w = e.getVertexOppositeTo(s);
            if (w.id == tid)  { // if w is the target, we found it, return true
                return true;
            }
            if (!w.seen) { // if w is not visited
                if (foundPathWithDfs(g, w.id, tid)) {
                    return true;
                }
            }
        }
        return false;
    }
}
