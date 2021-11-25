package playground.standford.apsp;

public class FloydWarshallAlgorithm {

    /**
     * Returns the minimum shortest path.
     */
    public long run(G g) {
        int n = g.size();
        long[][][] A = new long[n + 1][n][n];
        // initialize
        for(int i = 0; i < n; i ++) {
            for(int j = 0; j < n; j ++) {
                A[0][i][j] = initValue(g, i, j);
            }
        }
        long result = Long.MAX_VALUE;
        // compute
        for(int k = 1; k <= n; k ++) {
            System.out.println(k);
            for(int i = 0; i < n; i ++) {
                for(int j = 0; j < n; j ++) {
                    A[k][i][j] = Math.min(A[k-1][i][j], A[k-1][i][k-1] + A[k-1][k-1][j]);
                    if (A[k][i][j] < result) {
                        result = A[k][i][j];
                    }

                }
            }
        }

        // detect negative cycles
        for(int i = 0; i < n; i ++) {
            if (A[n][i][i] < 0) {
                throw new RuntimeException(
                        "negative cost cycle detected, A[" + n + "][" + i + "][" + i + "]=" + A[n][i][i]);
            }
        }
        return result;
    }

    private static long initValue(G g, int i, int j) {
        if (i == j) {
            return 0L;
        }
        E e = g.edge(i, j);
        if (e != null) {
            return e.length;
        }
        return Integer.MAX_VALUE;
    }

}
