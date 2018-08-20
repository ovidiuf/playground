package playground.dsa.quicksort;

import playground.dsa.common.SortingAlgorithm;

public class Quicksort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] ints) {

        quicksort(ints, 0, ints.length);

        return ints;
    }

public void quicksort(int[] a, int p, int r) {

    if (p < r - 1) {

        int q = partition(a, p, r);

        quicksort(a, p, q);
        quicksort(a, q, r);
    }
}

private int partition(int[] a, int p, int r) {

    int pivot = a[r - 1];

    int i = p - 1;

    for(int j = p; j < r - 1; j ++) {

        if (a[j] <= pivot) {

            i = i + 1;

            //
            // swap i, j
            //

            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    //
    // swap i + 1 and the pivot
    //

    int q = i + 1;

    int tmp = a[q];
    a[q] = a[r - 1];
    a[r - 1] = tmp;

    return q;
}

}
