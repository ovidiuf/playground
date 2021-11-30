package playground.stanford.twosat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class TwoSATInstance {
    public int n; // number of variables
    public boolean[] x;
    public int m; // number of clauses
    // each clause uses 4 ints:
    // negation (or not) for xi as 1 or 0, i (the index of the variable), negation(or not) for xj, j (the index of the variable)
    public int[] c;

    private final Random random = new Random();

    public TwoSATInstance(String fileName) throws Exception {
        load(fileName);
    }

    private long t0;
    private static final DateFormat DF = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");

    /**
     * @return true is the instance is satisfiable, false otherwise.
     */
    public boolean runRandomizedLocalSearch() {
        for(int i = 0; i < Math.log10(n)/Math.log10(2); i ++) {
            randomAssignment();
            //System.out.println(variablesToString());
            long iterations = 2L * (long)n * n;
            System.out.println(iterations + " iterations to go");
            long counter = 0;
            t0 = System.currentTimeMillis();
            for(long j = 0; j < iterations; j ++ ) {
                counter ++;
                report(counter, iterations);
                int[] indices = evaluateClauses();
                if (indices == null) {
                    // all clauses are satisfied
                    return true;
                }
                // flip the value of one of the variables that causes the previous clause to be unsatisfied
                boolean b = random.nextBoolean();
                int k = 0;
                if (b) {
                    k = 1;
                }
                // flip the value of the chosen variable
                boolean crt = x[indices[k]];
                x[indices[k]] = !crt;
            }
        }
        return false;
    }

    private void randomAssignment() {
        for(int i = 0; i < n; i ++) {
            x[i] = random.nextBoolean();
        }
    }

    /**
     * @return the indices of the variables for the first unsatisfied clause, or null if all the clauses are satisfied
     */
    private int[] evaluateClauses() {
        for(int i = 0; i < m; i ++) {
            boolean a = x[c[4 * i + 1]];
            if (c[4 * i] == 1) {
                a = !a;
            }
            boolean b = x[c[4 * i + 3]];
            if (c[4 * i + 2] == 1) {
                b = !b;
            }
            if (!(a || b)) {
                // clause unsatisfied, return the indices of the variables
                return new int[] {c[4 * i + 1], c[4 * i + 3]};
            }
        }
        return null;
    }

    private void report(long counter, long iterations) {
        long t1 = System.currentTimeMillis();
        if (counter % 100000 == 0) {
            long toGoMs = (long) ((double)(iterations - counter)/counter * (t1 - t0));
            System.out.println(counter + " iteration completed, estimated completion time: " + DF.format(System.currentTimeMillis() + toGoMs));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("n=").append(n).append(", m=").append(m).append("\n");
        sb.append(variablesToString());
        sb.append("\n");
        for(int i = 0; i < m; i ++) {
            sb.append(c[4 * i] == 1 ? "¬" : "");
            sb.append("x").append(c[4 * i + 1] + 1);
            sb.append("∨");
            sb.append(c[4 * i + 2] == 1 ? "¬" : "");
            sb.append("x").append(c[4 * i + 3] + 1);
            if (i < n - 1) {
                sb.append(" ∧ ");
            }
        }
        return sb.toString();
    }

    private String variablesToString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i ++) {
            sb.append(x[i] ? 't' : 'f');
            if (i < n - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    private void load(String fileName) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine().trim();
            n = Integer.parseInt(line);
            m = n;
            x = new boolean[n];
            c = new int[4*n];
            long counter = 0;
            int i = 0;
            while((line = br.readLine()) != null) {
                counter ++;
                String[] tok = line.trim().split(" ");
                int a = Integer.parseInt(tok[0]);
                int b = Integer.parseInt(tok[1]);
                // -16808 75250
                if (a < 0) {
                    c[i++] = 1; // negation
                    a = -1 * a;
                }
                else {
                    c[i++] = 0;
                }
                assert a >= 1;
                assert a <= n;
                c[i++] = a - 1; // index in x
                if (b < 0) {
                    c[i++] = 1; // negation
                    b = -1 * b;
                }
                else {
                    c[i++] = 0;
                }
                assert b >= 1;
                assert b <= n;
                c[i++] = b - 1; // index in x
            }
            assert counter == m;
        }
    }
}
