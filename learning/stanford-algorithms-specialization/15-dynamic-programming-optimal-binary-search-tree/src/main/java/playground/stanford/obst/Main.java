package playground.stanford.obst;

public class Main {

    public static void main(String[] args) {
        int n = 7;
        //float[] p = {0.0f, 0.05f, 0.4f, 0.08f, 0.04f, 0.1f, 0.1f, 0.23f};
        float[] p = {0.0f, 0.2f, 0.05f, 0.17f, 0.1f, 0.2f, 0.03f, 0.25f};
        float[][] A = new float[n+1][n+1];
        for(int s = 0; s < n; s ++) {
            for(int i = 1; i <= n; i ++) {
                if (i + s > n) {
                    // we run over the edge of the matrix
                    continue;
                }
                // compute min for r = 1 ... i +s
                float min = Float.MAX_VALUE;
                for(int r = i; r <= i+s; r ++) {
                    float f = 0.0f;
                    for(int k = i; k <= i+s; k ++) {
                        f += p[k];
                    }
                    f += (i > r - 1) ? 0 : A[i][r - 1];
                    f += (r + 1 > i + s) ? 0 : A[r + 1][i + s];
                    if (f < min) {
                        min = f;
                    }
                }
                A[i][i+s] = min;
            }
        }
        System.out.println(A[1][n]);
    }
}
