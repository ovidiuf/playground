package playground.standford.apsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A graph represented using adjacency lists. Undirected graphs include two edge representations for two vertices,
 * one from v to u and one from u to v. Directed graphs use just one edge representation.
 */
public class G {
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
     * @return the vertex with the given index.
     */
    public V vertex(int id) {
        return adj[id];
    }

    /**
     * @return n
     */
    public int size() {
        return adj.length;
    }

    /**
     * @return the corresponding edge, or null if there is no such edge. Throws IllegalArgumentException for invalid
     * vertex ids.
     */
    public E edge(int vid, int wid) {
        if (vid < 0 || vid >= size()) {
            throw new IllegalArgumentException("invalid vertex id " + vid);
        }
        if (wid < 0 || wid >= size()) {
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
        Map<Integer, V> vertices = new HashMap<>();
        // depends on external representation
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine().trim();
            String[] tok = line.split(" ");
            int n = Integer.parseInt(tok[0]);
            int m = Integer.parseInt(tok[1]);
            int maxid = Integer.MIN_VALUE;
            for(int i = 0; i < m; i ++) {
                line = br.readLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.charAt(0) == '#') {
                    continue;
                }
                tok = line.split(" ");
                int vi = Integer.parseInt(tok[0]);
                if (vi > maxid) {
                    maxid = vi;
                }
                int ui = Integer.parseInt(tok[1]);
                if (ui > maxid) {
                    maxid = ui;
                }
                int length = Integer.parseInt(tok[2]);
                V v = vertices.get(vi - 1);
                if (v == null) {
                    v = new V(vi - 1);
                    vertices.put(vi - 1, v);
                }
                V u = vertices.get(ui - 1);
                if (u == null) {
                    u = new V(ui - 1);
                    vertices.put(ui - 1, u);
                }
                v.edges.add(new E(this, u.id, length));
            }
            assert n == maxid;
            this.adj = new V[n];
            for(int i = 0; i < n; i ++) {
                adj[i] = vertices.get(i);
                if (adj[i] == null) {
                    // the vertex is not part of any edge
                    adj[i] = new V(i);
                    System.out.println("found unconnected vertex: " + adj[i]);
                }
            }
        }
    }
}

/**
 * A vertex. An optimized representation may get away without a dedicated class, by only maintaining indices,
 * but this becomes insufficient when we need to maintain auxiliary information per node, as required by
 * various algorithms (BFS and DFS need to know whether a node was visited or not).
 */
class V {
    /**
     * Redundant index, it helps with displaying the structure.
     */
    public int id;
    public List<E> edges;
    /**
     * Augmentation useful to search algorithms.
     */
    public boolean seen;
    public V(int id) {
        this.id = id;
        this.edges = new ArrayList<>();
        this.seen = false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder((id < 10 ? " " : "") + id + "[");
        if (seen) {
            s.append('*');
        }
        s.append("] → {");
        for(int i = 0; i < edges.size(); i ++) {
            s.append(edges.get(i));
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
    public int length;
    /**
     * The index of the adjacent (distant) vertex.
     */
    public int w;
    public E(G g, int w, int length) {
        this.g = g;
        this.w = w;
        this.length = length;
    }

    public V getVertexOppositeTo(V v) {
        // we assume the vertex is correct, and we return the only vertex we know, the "opposite" one
        return g.vertex(w);
    }

    @Override
    public String toString() {
        return w + "(" + length + ")";
    }
}