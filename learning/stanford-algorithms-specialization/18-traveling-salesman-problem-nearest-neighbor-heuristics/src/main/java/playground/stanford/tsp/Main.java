package playground.stanford.tsp;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "./data/nn.txt";
        G g = new G(fileName);
        TravelingSalesmanNearestNeighborHeuristics h = new TravelingSalesmanNearestNeighborHeuristics();
        double d = h.run(g);
        System.out.println(d);

    }
}
