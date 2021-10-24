package playground.stanford.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")
public class Graph {

    public int vertexCount;
    public int edgeCount;
    public final List<Vertex> adj;

    public Graph(String fileName) throws Exception {
        this.adj = new ArrayList<>();
        this.edgeCount = 0;
        load(fileName);
    }

    @Override
    public String toString() {
        return "n=" + size() + ", m=" + edgeCount;
    }

    public int size() {
        return adj.size();
    }

    /**
     * @param i 0-based vertex label
     */
    public Vertex getVertex(int i) {
        return adj.get(i);
    }

    public void dump() {
        for (Vertex v : adj) {
            System.out.println(v.label + ": " + v.edges);
        }
    }

    /**
     * @return each edge twice
     */
    public Iterator<Edge> edgeIterator() {
        List<Edge> edges = new ArrayList<>();
        for (Vertex v : adj) {
            edges.addAll(v.edges);
        }
        return edges.iterator();
    }

    public List<Edge> getDistinctEdges() {
        List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < adj.size(); i ++) {
            Vertex v = adj.get(i);
            for(Edge e: v.edges) {
                if (e.v2 < i) {
                    // we've already seen this edge
                    continue;
                }
                edges.add(e);
            }
        }
        return edges;
    }

    private void load(String fileName) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = br.readLine().trim();
            this.vertexCount = Integer.parseInt(line);
            for (int i = 0; i < vertexCount; i++) {
                adj.add(new Vertex(i));
            }
            while((line = br.readLine()) != null) {
                line = line.trim();
                String[] tok = line.split(" ");
                int v1Index = Integer.parseInt(tok[0]) - 1;
                assert 0 <= v1Index;
                assert v1Index < vertexCount;
                int v2Index = Integer.parseInt(tok[1]) - 1;
                assert 0 <= v2Index;
                assert v2Index < vertexCount;
                int cost = Integer.parseInt(tok[2]);
                Edge e = new Edge(v1Index, v2Index, cost);
                adj.get(v1Index).addEdge(e);
                Edge e2 = new Edge(v2Index, v1Index, cost);
                adj.get(v2Index).addEdge(e2);
                edgeCount ++;
            }
        }
    }
}
