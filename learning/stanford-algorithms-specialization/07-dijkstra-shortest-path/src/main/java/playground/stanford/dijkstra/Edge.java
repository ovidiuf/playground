package playground.stanford.dijkstra;

public class Edge {

    public int length;
    public int v1;
    public int v2;

    /**
     * @param v1 0-based vertex label for the "proximal" vertex.
     * @param v2 0-based vertex label for the "distant" vertex.
     * @param length the edge length
     */
    public Edge(int v1, int v2, int length) {
        this.v1 = v1;
        this.v2 = v2;
        this.length = length;
        assert this.length >= 0;
    }

    @Override
    public String toString() {
        return v1 + "-" + length + "-" + v2;
    }
}
