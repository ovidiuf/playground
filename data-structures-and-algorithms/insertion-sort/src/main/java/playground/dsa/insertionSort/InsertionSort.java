package playground.dsa.insertionSort;

import playground.dsa.common.SortingAlgorithm;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] a) {

        if (a.length == 0 || a.length == 1) {

            return a;
        }

        //
        // going forward, we assume the array has at least two elements
        //

        for(int i = 1; i < a.length; i ++) {

            //
            // if larger or equal, the key is in the right position, advance ...
            //
            if (a[i] >= a[i - 1]) {
                continue;
            }

            //
            // The key is smaller than the last one in the sorted subarray,
            // send it through the subarray until it reaches the correct position.
            // Use a different index variable.

            int j = i - 1; // this is the first element of the sorted subarray

            //
            // order in which comparison expressions are evaluated is important,
            // we use short-circuiting if j is negative
            //
            while(j >= 0 && a[j] > a[j + 1]) {

                int key = a[j + 1];
                a[j + 1] = a[j];
                a[j] = key;
                j --;

            }
        }

        return a;
    }
}
