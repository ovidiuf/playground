package playground.stanford.tsp;

import java.util.Iterator;
import java.util.Map;

public class TravelingSalesmanProblem {

    public final G g;

    public TravelingSalesmanProblem(String fileName) throws Exception {
        this.g = new G(fileName);
    }

    public double run() {
        int n = g.size();
        System.out.println("n: " + n + ", computing subsets ...");
        Subsets subsets = new Subsets(n);
        System.out.println("allocating array A["+ subsets.size() +"][" + (n + 1) + "] ...");
        double[][] A = new double[subsets.size()][n + 1];
        System.out.println("initializing base case ...");
        // base case
        A[0][1] = 0;
        for(int i = 1; i < A.length; i ++) {
            A[i][1] = Long.MAX_VALUE;
        }
        System.out.println("running main loop ...");
        for(int m = 2; m <= n; m ++) {
            Map<Subset, Subset> subsetsOfSameSize =  subsets.getSubsetsOfSize(m);
            System.out.println("running for m = " + m + ", " + subsetsOfSameSize.size() + " subsets ...");
            long count = 0;
            for(Subset s: subsetsOfSameSize.keySet()) {
                report(++count);
                for(Iterator<Integer> ei = s.elements(); ei.hasNext(); ) {
                    int j = ei.next();
                    if (j == 1) {
                        continue;
                    }
                    double min = Long.MAX_VALUE;
                    for(Iterator<Integer> ei2 = s.elements(); ei2.hasNext(); ) {
                        int k = ei2.next();
                        if (k == j) {
                            continue;
                        }
                        int sWithoutJIndex = subsets.getIndexOfSubsetWithoutSpecifiedElement(s, j);
                        double v = A[sWithoutJIndex][k] + g.edge(k, j).distance;
                        if (v < min) {
                            min = v;
                        }
                    }
                    A[s.id()][j] = min;
                }
            }
        }

        double min = Long.MAX_VALUE;
        int fullSetIndex = subsets.getIndexOf(new FastSubset(n, allElements(n)));
        for(int j = 2; j <= n; j ++) {
            double v = A[fullSetIndex][j] + g.edge(j, 1).distance;
            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    private void report(long count) {
        if (count % 1000000 == 0) {
            System.out.println("   " + count + "  ✓");
        }

    }

    private int[] allElements(int n) {
        int[] a = new int[n];
        for(int i = 1; i <= n; i ++) {
            a[i - 1] = i;
        }
        return a;
    }
}
