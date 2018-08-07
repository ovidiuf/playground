package playground.dsa.selectionSort;

import playground.dsa.common.SortingAlgorithm;

public class SelectionSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] a) {

        if (a.length == 0 || a.length == 1) {

            return a;
        }

        for(int i = 0; i < a.length - 1; i ++) {

            int minIndex = i;

            for(int j = i + 1; j < a.length; j ++) {

                if (a[j] < a[minIndex]) {

                    minIndex = j;
                }
            }

            if (i != minIndex) {

                //
                // swap
                //

                int tmp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = tmp;
            }
        }

        return a;
    }
}
