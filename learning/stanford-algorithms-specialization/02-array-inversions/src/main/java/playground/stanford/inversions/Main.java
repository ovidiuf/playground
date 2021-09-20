package playground.stanford.inversions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Integer[] values = readData();
        //Integer[] values = new Integer[] { 5, 1, 6, 2 };
        Result result = inversions(values);
        System.out.println(result.inversionCount);
    }

    public static Result inversions(Integer[] values) {
        if (values.length <= 1 ) {
            return new Result(0, values);
        }
        int middle = values.length / 2;
        Integer[] left = new Integer[middle];
        Integer[] right = new Integer[values.length - middle];
        System.arraycopy(values, 0, left, 0, left.length);
        System.arraycopy(values, middle, right, 0, right.length);
        Result leftResult = inversions(left);
        Result rightResult = inversions(right);
        Integer[] B = leftResult.sortedArray;
        Integer[] C = rightResult.sortedArray;
        Integer[] D = new Integer[B.length + C.length];
        int splitInversions = 0;
        int i = 0;
        int j = 0;
        int k = 0;
        while(k < D.length) {
            if (i < B.length && (j == C.length || B[i] <= C[j])) {
                D[k] = B[i];
                i ++;
            }
            else {
                // all elements from i to the end of the B array are inversions
                // if we went over the edge, then B.length - i is 0 so the edge case is handled
                splitInversions += (B.length - i);
                D[k] = C[j];
                j ++;
            }
            k ++;
        }
        return new Result(leftResult.inversionCount + rightResult.inversionCount + splitInversions, D);
    }

    public static Integer[] readData() throws Exception {
        File f = new File("./data/integers.txt");
        BufferedReader r = new BufferedReader(new FileReader(f));
        List<Integer> ints = new ArrayList<>();
        String line;
        while((line = r.readLine()) != null) {
            ints.add(Integer.parseInt(line.trim()));
        }
        r.close();
        Integer[] values = new Integer[ints.size()];
        return ints.toArray(values);
    }
}

class Result {
    long inversionCount;
    Integer[] sortedArray; // never null, may be empty

    public Result(long count, Integer[] sortedArray) {
        if (sortedArray == null) {
            throw new IllegalArgumentException("null sortedArray");
        }
        this.inversionCount = count;
        this.sortedArray = sortedArray;
    }
}
