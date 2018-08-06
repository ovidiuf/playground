package playground.dsa.insertionSort;

import playground.dsa.common.DSA;

public class Main {

    public static void main(String[] args) {

//        int[] a = DSA.unsortedArray(20, 10);
//
//        DSA.printArray(a);
//
//        new InsertionSort().sort(a);
//
//        DSA.printArray(a);

        DSA.test(100000, 400, 20, new InsertionSort(), false);


    }
}
