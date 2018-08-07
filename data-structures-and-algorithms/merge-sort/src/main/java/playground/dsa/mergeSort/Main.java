package playground.dsa.mergeSort;

import playground.dsa.common.DSA;

public class Main {

    public static void main(String[] args) {

        int a[] = DSA.unsortedArray(11, 50);

        DSA.printArray(a);

        new MergeSort().sort(a);

        DSA.printArray(a);

        DSA.test(1000, 100, 50, new MergeSort(), false);

    }
}
