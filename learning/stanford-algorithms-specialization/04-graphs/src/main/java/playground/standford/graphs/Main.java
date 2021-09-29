package playground.standford.graphs;

import java.io.File;

public class Main {

    private static int min = Integer.MAX_VALUE;

    public static void main(String[] args) {
        File f = new File("./data/kargerMinCut.txt");
        //File f = new File("./data/test1.txt");
        //File f = new File("./data/test2.txt");

        for(int i = 0; i < 1000; i ++) {
            Graph g = new AdjacencyListGraph(f);
            //g.display();
            int ce = g.randomizedKargerContraction();
            min = Math.min(min, ce);
            System.out.println(min + ", " + ce);
        }
    }
}
