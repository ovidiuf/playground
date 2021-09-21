package playground.stanford.quicksort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Main {

    private static long comparisonCount = 0;
    private static PivotType PIVOT_TYPE;

    public static void main(String[] args) throws Exception {

        PIVOT_TYPE = PivotType.MEDIAN;
//        int[] a = readDataFromCommandLine(args);
        int[] a = readData();
        quicksort(a, 0, a.length - 1);
        display(a);
        System.out.println("comparison count: " + comparisonCount);
    }

    public static void quicksort(int[] a, int first, int last) {
        if (first >= last) {
            return;
        }

        // try various types of pivots, and count comparisons
        comparisonCount += (last - first);
        int pivotIndex;
        if (PIVOT_TYPE.equals(PivotType.FIRST)) {
            pivotIndex = Partition.pivotFirstElement(a, first, last);
        }
        else if (PIVOT_TYPE.equals(PivotType.LAST)) {
            pivotIndex = Partition.pivotLastElement(a, first, last);
        }
        else if (PIVOT_TYPE.equals(PivotType.MEDIAN)) {
            pivotIndex = Partition.pivotMedianOfThree(a, first, last);
        }
        else {
            throw new IllegalStateException();
        }

        quicksort(a, first, pivotIndex - 1);
        quicksort(a, pivotIndex + 1, last);
    }

    public static int[] readDataFromCommandLine(String[] args) {
        int[] result = new int[args.length];
        for(int i = 0; i < args.length; i ++) {
            result[i] = Integer.parseInt(args[i]);
        }
        return result;
    }

    public static int[] readData() throws Exception {
        File f = new File("./data/integers.txt");
        BufferedReader r = new BufferedReader(new FileReader(f));
        List<Integer> ints = new ArrayList<>();
        String line;
        while((line = r.readLine()) != null) {
            ints.add(Integer.parseInt(line.trim()));
        }
        r.close();
        int[] values = new int[ints.size()];
        for(int i = 0; i < values.length; i ++) {
            values[i] = ints.get(i);
        }
        return values;
    }

    public static void display(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
