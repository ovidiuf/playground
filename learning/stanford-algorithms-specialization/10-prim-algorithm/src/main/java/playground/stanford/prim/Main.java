package playground.stanford.prim;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "./data/edges.txt";
        //String fileName = "./data/test1.txt";
        Graph g = new Graph(fileName);
        //g.dump();
        PrimAlgorithm p = new PrimAlgorithm();
        p.run(g);
        System.out.println("minimum spanning tree cost: " + p.spanningTreeCost);
    }
}
