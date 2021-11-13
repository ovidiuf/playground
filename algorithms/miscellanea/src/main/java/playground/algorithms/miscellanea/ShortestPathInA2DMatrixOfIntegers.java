package playground.algorithms.miscellanea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * Given a 2D matrix of integers, where 0 is traversable and 1 is not, find the shortest path from the top left
 * corner to the bottom right corner.  Function should take in a matrix, and return a list of coordinates of the
 * shortest path.
 *
 * For example, for the following board:
 * 0 0 0 0 0
 * 0 0 0 0 1
 * 1 1 0 1 0
 * 0 0 0 0 0 -> exit
 *
 * This is a possible path
 * X X X X 0
 * 0 0 X X 0
 * 1 1 X 1 0
 * 0 0 X X X -> exit
 *
 * But this is the shortest path:
 *
 * X X X 0 0
 * 0 0 X 0 1
 * 1 1 X 1 0
 * 0 0 X X X -> exit
 *
 * [(0, 0), (0, 1), (0, 2), (1, 2), (2, 2), (3, 2), (3, 3), (3, 4)]
 */
public class ShortestPathInA2DMatrixOfIntegers {
    public void run() throws Exception {
        int[][] matrix =
                {
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1},
                        {1, 1, 0, 1, 0},
                        {0, 0, 0, 0, 0},
                };
        G g = new G(matrix);
        shortestPathWithBFS(g, 0, g.id(3, 4));
        List<V> shortestPath = backtrackShortestPath(g, g.id(3, 4));
        for(int i = shortestPath.size() - 1; i >= 0; i --) {
            System.out.print(shortestPath.get(i).label);
            if (i > 0) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    /**
     * The method walks the graph forwards and finds the shortest path from source to target, while annotating the
     * graph. To actually get the path, use reportShortestPath()
     */
    public static void shortestPathWithBFS(G g, int s, int t) {
        // mark all graph nodes as unvisited
        // iterate over graph nodes starting from s
        // collect path in r
        V start = g.get(s);
        start.visited = true;
        start.distance = 0;
        Q<V> q = new Q<>(g.adj.length);
        q.enqueue(start);
        while(!q.isEmpty()) {
            // all vertices in queue are explored
            V u = q.dequeue();
            for(E e: u.edges) {
                V v = g.get(e.v);
                if (!v.visited) {
                    v.visited = true;
                    v.distance = u.distance + 1;
                    if (v.id == t) {
                        System.out.println("shortest path length " + g.get(t).distance);
                    }
                    q.enqueue(v);
                }
            }
        }
    }

    public List<V> backtrackShortestPath(G g, int t) {
        List<V> l = new ArrayList<>();
        l.add(g.get(t));
        int v = t;
        while(true) {
            for(E e: g.get(v).edges) {
                V u = g.get(e.v);
                if (u.distance == g.get(v).distance - 1) {
                    l.add(u);
                    if (u.distance == 0) {
                        return l;
                    }
                    v = u.id;
                    break;
                }
            }
        }
    }
}

class G {
    private int[][] m;
    public V[] adj;
    public G(int[][] matrix) {
        load(matrix);
    }

    public V get(int id) {
        return adj[id];
    }

    public void dump() {
        for (V v : adj) {
            System.out.println(v.id + ": " + v.edges);
        }
    }

    private void load(int[][] m) {
        this.m = m;
        this.adj = new V[m.length * m[0].length];

        for(int i = 0; i < m.length; i ++) {
            for(int j = 0; j < m[0].length; j ++) {
                int id = id(i, j);
                String label = "(" + i + ", " + j + ")";
                int value = m[i][j];
                V v = new V(id, label);
                adj[id] = v;
                if (value == 1) {
                    // no edges incident on this node
                    System.out.println("no edges vertex " + v);
                    continue;
                }
                else if (value != 0 ) {
                    throw new IllegalArgumentException(i + ", " + j + " contains invalid value: " + value);
                }
                // there are four possible incident edges
                // left
                if (j - 1 >= 0) {
                    if (m[i][j - 1] == 0) {
                        v.addEdge(id(i, j-1));
                    }
                }
                // up
                if (i - 1 >= 0) {
                    if (m[i - 1][j] == 0) {
                        v.addEdge(id(i - 1, j));
                    }
                }
                // right
                if (j + 1 < m[0].length) {
                    if (m[i][j + 1] == 0) {
                        v.addEdge(id(i, j + 1));
                    }
                }
                // down
                if (i + 1 < m.length) {
                    if (m[i + 1][j] == 0) {
                        v.addEdge(id(i + 1, j));
                    }
                }
            }
        }
    }

    int id(int i, int j) {
        return i * m[0].length + j;
    }
}

class V {
    public int id;
    public String label; // (x,y)
    public List<E> edges;
    public boolean visited;
    public int distance;
    public V(int id, String label) {
        this.id = id;
        this.label = label;
        this.edges = new ArrayList<>();
    }
    public void addEdge(int v) {
        for(E e: edges) {
            if (e.v == v) {
                throw new IllegalArgumentException("edge to " + v + " already exists");
            }
        }
        edges.add(new E(v));
    }
    @Override
    public String toString() {
        return "" + id;
    }
}

class E {
    public int v;
    public E(int v) {
        this.v = v;
    }
    @Override
    public String toString() {
        return "→" + v;
    }
}




