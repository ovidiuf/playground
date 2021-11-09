package playground.graphs.adj;

import java.util.Arrays;

@SuppressWarnings("SameParameterValue")
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(APath.foundPathWithDfs(new G("./data/directed1.txt"), 10, 11));
    }

    private static void bfs() throws Exception {
        String fileName = "./data/undirected1.txt";
        G g = new G(fileName);
        Arrays.stream(g.adj).forEach(v -> v.seen = false); // mark all graph vertices as not "seen"
        System.out.println("Before search:\n" + g);
        new BFS().bfs(g, 0);
        System.out.println("After search:\n" + g);
    }

    private static void dfs() throws Exception {
        String fileName = "./data/undirected1.txt";
        G g = new G(fileName);
        Arrays.stream(g.adj).forEach(v -> v.seen = false); // mark all graph vertices as not "seen"
        System.out.println("Before search:\n" + g);
        new DFS().dfs(g, 16);
        System.out.println("After search:\n" + g);
    }
}
