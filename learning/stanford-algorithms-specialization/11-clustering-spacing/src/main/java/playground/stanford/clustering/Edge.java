package playground.stanford.clustering;

public class Edge implements Comparable<Edge> {

    public int cost;
    public int v1;
    public int v2;

    /**
     * @param v1 0-based vertex label for the "proximal" vertex.
     * @param v2 0-based vertex label for the "distant" vertex.
     * @param cost the edge cost (may be negative)
     */
    public Edge(int v1, int v2, int cost) {
        this.v1 = v1;
        this.v2 = v2;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return v1 + "‹" + cost + "›" + v2;
    }

    @Override
    public int compareTo(Edge that) {
        return cost - that.cost;
    }
}
