package playground.standford.graphs;

/**
 * Undirected edge. It may be a self loop (node == node2).
 */
@SuppressWarnings("ManualMinMaxCalculation")
public class Edge {
    public int node; // 0-based
    public int node2; // 0-based
    public Edge(int node, int node2) {
        this.node = node;
        this.node2 = node2;
    }
    public int smallest() {
        if (node <= node2) {
            return node;
        }
        return node2;
    }
    public int largest() {
        if (node > node2) {
            return node;
        }
        return node2;
    }

    @Override
    public String toString() {
        return "(" + node + ", " + node2 + ")";
    }
}
