package playground.stanford.scc;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "./data/scc.txt";
        //String fileName = "./data/test.txt";
        //String fileName = "./data/test3.txt";
        Graph g = new Graph(new File(fileName));
        new Kosaraju().run(g);
    }
}
