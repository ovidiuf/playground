package playground.stanford.knapsack;

import java.io.BufferedReader;
import java.io.FileReader;

public class Knapsack {

    public int n; // the number of items
    public int W; // capacity
    public int[] v; // individual values, we'll leave v[0] unused and use 1-based indices for clarity of the algorithm
    public int[] w; // individual sizes, we'll leave w[0] unused and use 1-based indices for clarity of the algorithm

    public Knapsack(String fileName) throws Exception {
        load(fileName);
    }

    public int findMaximumValue() {
        // the dynamic programming array, indexed by item number and weight, using 1-based indices
        int[][] V = new int[n + 1][W + 1];
        for(int x = 0; x <= W; x ++) {
            // the array is already initialized with 0
            assert V[0][x] == 0;
        }
        for(int i = 1; i <= n; i ++) { // iterate over items
            System.out.println("item " + i);
            for(int x = 0; x <= W; x ++) { // iterate over discrete sizes
                V[i][x] = Math.max(
                        V[i-1][x],
                        (x < w[i] ? Integer.MIN_VALUE : V[i-1][x-w[i]] + v[i]));
            }
        }
        int maxValue = V[n][W];
        // TODO: find the items that contributed to the solution
        return maxValue;
    }

    @Override
    public String toString() {
        return "items: " + n + ", size: " + W;
    }

    private void load(String fileName) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = br.readLine().trim();
            String[] tok = line.split(" ");
            this.W = Integer.parseInt(tok[0]);
            this.n = Integer.parseInt(tok[1]);
            this.v = new int[n + 1];
            this.w = new int[n + 1];
            for(int i = 1; i <= n; i ++) {
                line = br.readLine().trim();
                tok = line.split(" ");
                v[i] = Integer.parseInt(tok[0]);
                w[i] = Integer.parseInt(tok[1]);
            }
        }
    }
}
