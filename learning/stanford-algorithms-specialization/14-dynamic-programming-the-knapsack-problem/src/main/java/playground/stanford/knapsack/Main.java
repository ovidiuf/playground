package playground.stanford.knapsack;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "./data/knapsack1.txt";
        //String fileName = "./data/knapsack_big.txt";
        //String fileName = "./data/test1.txt";
        Knapsack k = new Knapsack(fileName);
        System.out.println(k);
        int v = k.findMaximumValue();
        System.out.println(v);
    }
}
