package playground.stanford.dijkstra;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        //String fileName = "./data/test1.txt";
        String fileName = "./data/dijkstraData.txt";
        Graph g = new Graph(fileName);
        NonOptimizedDijkstra nonOptimizedDijkstra = new NonOptimizedDijkstra();
        nonOptimizedDijkstra.run(g, 0);

        List<Integer> controlVertices = Arrays.asList(7,37,59,82,99,115,133,165,188,197);
        for(Iterator<Integer> i = controlVertices.iterator(); i.hasNext(); ) {
            int v = i.next() - 1; // adjust for 0-based indices
            System.out.print(nonOptimizedDijkstra.getShortestPathTo(v));
            if (i.hasNext()) {
                System.out.print(",");
            }
        }
        System.out.println();
   }
}
