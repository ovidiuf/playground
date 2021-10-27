package playground.stanford.mwis;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        //String fileName = "./data/test1.txt";
        String fileName = "./data/mwis.txt";
        long[] w = load(fileName);
        maximumWeightIndependentSet(w);
    }

    private static void maximumWeightIndependentSet(long[] w) {
        long[] W = new long[w.length + 1];
        W[0] = 0;
        W[1] = w[0]; // weights are zero-based in w
        //
        // compute the maximum weight independent sets for 1, 2 ... n prefix sub-graphs
        // when the loop is over, the maximum weight is found in W[n]
        //
        for(int i = 2; i < W.length; i ++) {
            W[i] = Math.max(W[i-1], w[i-1] + W[i-2]);
        }

        System.out.println("maximum weight: " + W[w.length]);

        //
        // walk back the vertex array and display the vertices that belong to the maximum weight independent set
        //
        for(int i = w.length; i >= 1; ) {

            if (i == 1) {
                System.out.println(i);
                break;
            }
            else if (w[i - 1] + W[i - 2] == W[i]) {
                // vertex ith element belongs to the independent set and for sure i-1 doesn't
                System.out.println(i);
                i = i - 2;
            }
            else {
                // vertex ith element does not belong to the independent set
                i = i - 1;
            }
        }
    }

    /**
     * @return 0-based weight array. Vertex 1 has weight w[0], etc.
     */
    private static long[] load(String fileName) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int count = Integer.parseInt(br.readLine().trim());
            long[] result = new long[count];
            for(int i = 0; i < count; i ++) {
                result[i] = Integer.parseInt(br.readLine().trim());
            }
            return result;
        }
    }
}
