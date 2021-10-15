package playground.stanford.dijkstra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class Graph {

    private final List<Vertex> adj;

    public Graph(String fileName) throws Exception {
        this.adj = new ArrayList<>();
        load(fileName);
    }

    @Override
    public String toString() {
        return "size=" + size();
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

    public List<Edge> getEdgesThatCrossTheBoundary(Set<Integer> X) {
        List<Edge> result = new ArrayList<>();
        for(Integer i: X) {
            List<Edge> edges = getVertex(i).edges;
            for(Edge e: edges) {
                if (!X.contains(e.v2)) {
                    // does cross the boundary
                    result.add(e);
                }
            }
        }
        return result;
    }

    public void dump() {
        for(Vertex v: adj) {
            System.out.println(v.label + ": " + v.edges );
        }
    }

    private void load(String fileName) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int counter = 0;
            while((line = br.readLine()) != null) {
                String[] toks = line.split("[ \t]");
                int vertexLabel = Integer.parseInt(toks[0]) - 1;
                assert counter == vertexLabel;
                Vertex v = new Vertex(vertexLabel);
                adj.add(v);
                for(int i = 1; i < toks.length; i ++) {
                    String edge = toks[i];
                    int j = edge.indexOf(',');
                    assert j != -1;
                    int vertex = Integer.parseInt(edge.substring(0, j)) - 1;
                    int length = Integer.parseInt(edge.substring(j + 1));
                    Edge e = new Edge(vertexLabel, vertex, length);
                    v.addEdge(e);
                }
                counter ++;
            }
            System.out.println(counter + " vertices");
        }
    }

}
