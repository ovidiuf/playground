package playground.dsa.selectionSort;

import playground.dsa.common.DSA;

public class Main {

    public static void main(String[] args) {

        int[] a = DSA.unsortedArray(10, 10);

        DSA.printArray(a);

        new SelectionSort().sort(a);

        DSA.printArray(a);

        DSA.test(100000, 400, 20, new SelectionSort(), false);
    }
}
