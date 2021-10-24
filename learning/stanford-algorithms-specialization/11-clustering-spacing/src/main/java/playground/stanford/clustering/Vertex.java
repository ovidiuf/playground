package playground.stanford.clustering;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    // 0-based label
    public int label;

    // the leader's label
    public int leader;

    List<Edge> edges;

    public Vertex(int label) {
        this.label = label;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    @Override
    public String toString() {
        return "" + label;
    }
}
