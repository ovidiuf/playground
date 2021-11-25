package playground.standford.apsp;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "./data/g1.txt";
        //String fileName = "./data/g2.txt";
        //String fileName = "./data/g3.txt";
        //String fileName = "./data/test1.txt";
        //String fileName = "./data/negative-cost-cycle.txt";
        G g = new G(fileName);
        System.out.println(g);
        long sp = new FloydWarshallAlgorithm().run(g);
        System.out.println(sp);
    }
}
