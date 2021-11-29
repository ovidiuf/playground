package playground.stanford.tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A graph represented using adjacency lists. Undirected graphs include two edge representations for two vertices,
 * one from v to u and one from u to v. Directed graphs use just one edge representation.
 *
 * 1-based indices.
 */
public class G {
    // 1-based indices
    public V[] adj;

    /**
     * DIRECTED/UNDIRECTED
     * edge0 as (u, v)
     * edge1 as (s, t)
     * ...
     * Allows comment lines starting with "#"
     * @param fileName - the file to load the graph description from
     */
    public G(String fileName) throws Exception {
        load(fileName);
    }

    /**
     * @return the vertex with the given index - 1-based index.
     */
    public V vertex(int id) {
        return adj[id];
    }

    /**
     * @return n
     */
    public int size() {
        return adj.length - 1;
    }

    /**
     * @return the corresponding edge, or null if there is no such edge. Throws IllegalArgumentException for invalid
     * vertex ids. Uses 1-based indices.
     */
    public E edge(int vid, int wid) {
        if (vid < 1 || vid > size()) {
            throw new IllegalArgumentException("invalid vertex id " + vid);
        }
        if (wid < 1 || wid > size()) {
            throw new IllegalArgumentException("invalid vertex id " + wid);
        }
        V v = vertex(vid);
        for(E e: v.edges) {
            if (e.w == wid) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (V v : adj) {
            sb.append(v).append("\n");
        }
        return sb.toString();
    }

    private void load(String fileName) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine().trim();
            int n = Integer.parseInt(line);
            adj = new V[n + 1];
            int i = 1;
            while(((line = br.readLine()) != null)) {
                line = line.trim();
                String[] tok = line.split(" ");
                int i2 = Integer.parseInt(tok[0]);
                assert i == i2;
                double x = Double.parseDouble(tok[1]);
                double y = Double.parseDouble(tok[2]);
                V v = new V(i, x, y);
                adj[i] = v;
                i ++;
            }
            assert n == i - 1;
            System.out.println("loaded all " + n + " vertices");
            // compute distances among vertices
//            for(i = 1; i <= n; i ++) {
//                V v = adj[i];
//                for(int j = 1; j <= n; j ++) {
//                    if (i == j || v.hasEdgeTo(j)) {
//                        continue;
//                    }
//                    V w = adj[j];
//                    double d = computeDistance(v, w);
//                    v.edges.add(new E(this, w.id, d));
//                    w.edges.add(new E(this, v.id, d));
//                }
//            }
        }
    }

    public double distance(V v1, V v2) {
        return Math.sqrt((v1.x - v2.x)*(v1.x - v2.x) + (v1.y - v2.y)*(v1.y - v2.y));
    }
}

/**
 * A vertex. An optimized representation may get away without a dedicated class, by only maintaining indices,
 * but this becomes insufficient when we need to maintain auxiliary information per node, as required by
 * various algorithms (BFS and DFS need to know whether a node was visited or not).
 */
class V {
    /**
     * Redundant 1-based index, it helps with displaying the structure.
     */
    public int id;
    public double x;
    public double y;
    public List<E> edges;
    public boolean visited;
    public V(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<>();
        this.visited = false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder((id < 10 ? " " : "") + id + "[");
        s.append("] → {");
        for(int i = 0; i < edges.size(); i ++) {
            E e = edges.get(i);
            s.append(e.w).append("(").append(e.distance).append(")");
            if (i < edges.size() - 1) {
                s.append(", ");
            }
        }
        s.append("}");
        return s.toString();
    }
}

/**
 * An edge. Useful to carry additional metadata, such as weight, etc. The same edge structure can be used for
 * directed and undirected graphs. An undirected graph will maintain edge representations in both directions.
 */
class E {
    private final G g;
    public double distance = 1;
    /**
     * The 1-based index of the adjacent (distant) vertex.
     */
    public int w;
    public E(G g, int w, double distance) {
        this.g = g;
        this.w = w;
        this.distance = distance;
    }

    public V getVertexOppositeTo(V v) {
        // we assume the vertex is correct, and we return the only vertex we know, the "opposite" one
        return g.vertex(w);
    }
}