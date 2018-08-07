package playground.dsa.mergeSort;

import playground.dsa.common.SortingAlgorithm;

public class MergeSort implements SortingAlgorithm {

    @Override
    public int[] sort(int[] a) {

        mergeSort(a, 0, a.length);
        return a;
    }

    /**
     * Merge sorts in-place.
     *
     * @param to indicates the first array element outside the area to sort. It may be equal with the
     *           array length, if we want to sort the entire array.
     */
    private void mergeSort(int[] a, int from, int to) {

        //
        // divide
        //

        int middle = from + (to - from) / 2;

        if (from == middle) {

            //
            // we bottomed out, it means a has at most one element, and it is already sorted,
            // get out of recurrence.
            //

            return;
        }

        //
        // there is a middle that separates non-empty arrays, conquer
        //

        mergeSort(a, from, middle);
        mergeSort(a, middle, to);

        //
        // combine sorted sub-arrays
        //

        merge(a, from, middle, to);
    }

    /**
     * Combines in place. Assumes that the sub-arrays [i, j) and [j, k) are sorted and merges them in-place.
     */
    private void merge(int[] a, int i, int j, int k) {

        //
        // make copies
        //

        int[] left = new int[j - i];
        System.arraycopy(a, i, left, 0, left.length);

        int[] right = new int[k - j];
        System.arraycopy(a, j, right, 0, right.length);

        int l = 0;
        int r = 0;
        int dest = i;

        while(l < left.length && r < right.length) {

            if (left[l] <= right[r]) {

                a[dest ++] = left[l];
                l ++;
            }
            else {

                a[dest ++] = right[r];
                r ++;
            }
        }

        //
        // drain leftovers
        //

        while(l < left.length) {

            a[dest ++] = left[l ++];
        }

        while(r < right.length) {

            a[dest ++] = right[r ++];
        }
    }
}
