package playground.graphs.adj;

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
            boolean directed = false;
            if ("DIRECTED".equals(line)) {
                directed = true;
            }
            else if (!"UNDIRECTED".equals(line)) {
                throw new IllegalArgumentException("expecting DIRECTED|UNDIRECTED and got " + line);
            }
            int maxid = Integer.MIN_VALUE;
            while(((line = br.readLine()) != null)) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.charAt(0) == '#') {
                    continue;
                }
                String[] tok = line.split(" ");
                int vi = Integer.parseInt(tok[0]);
                if (vi > maxid) {
                    maxid = vi;
                }
                int ui = Integer.parseInt(tok[1]);
                if (ui > maxid) {
                    maxid = ui;
                }
                V v = vertices.get(vi);
                if (v == null) {
                    v = new V(vi);
                    vertices.put(vi, v);
                }
                V u = vertices.get(ui);
                if (u == null) {
                    u = new V(ui);
                    vertices.put(ui, u);
                }
                v.edges.add(new E(this, u.id));
                if (!directed) {
                    u.edges.add(new E(this, v.id));
                }
            }
            this.adj = new V[maxid + 1];
            for(int i = 0; i <= maxid; i ++) {
                adj[i] = vertices.get(i);
                if (adj[i] == null) {
                    // the vertex is not part of any edge
                    adj[i] = new V(i);
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
            s.append(edges.get(i).w);
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
    public int weight = 1;
    /**
     * The index of the adjacent (distant) vertex.
     */
    public int w;
    public E(G g, int w) {
        this.g = g;
        this.w = w;
    }

    public V getVertexOppositeTo(V v) {
        // we assume the vertex is correct, and we return the only vertex we know, the "opposite" one
        return g.vertex(w);
    }
}
