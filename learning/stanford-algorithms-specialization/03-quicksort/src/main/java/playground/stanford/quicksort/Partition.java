package playground.stanford.quicksort;

@SuppressWarnings("unused")
public class Partition {

    /**
     * Canonical partition subroutine implementation.
     *
     * @return the final index of the pivot after partitioning.
     */
    public static int pivotFirstElement(int[] a, int first, int last) {
        int pivot = a[first];
        int i = first + 1;
        for(int j = first + 1; j <= last; j ++) {
            if (a[j] <= pivot) {
                // swap
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
                i ++;
            }
        }
        // place the pivot in the final position
        a[first] = a[i - 1];
        a[i - 1] = pivot;
        return (i - 1);
    }

    /**
     * Use pivotFirstElement() implementation, but swap the first element with the last one.
     *
     * @return the final index of the pivot after partitioning.
     */
    public static int pivotLastElement(int[] a, int first, int last) {
        // swap first with last
        int tmp = a[first];
        a[first] = a[last];
        a[last] = tmp;
        return pivotFirstElement(a, first, last);
    }

    public static int pivotMedianOfThree(int[] a, int first, int last) {
        int i = chooseMedian(a[first], a[(last - first)/2 + first], a[last]);
        int medianElementIndex = i == 0 ? first : (i == 1 ? (last - first)/2 + first : last);
        // swap first with medianElementIndex
        int tmp = a[first];
        a[first] = a[medianElementIndex];
        a[medianElementIndex] = tmp;
        return pivotFirstElement(a, first, last);
    }

    /**
     * Native implementation for using the last element as pivot.
     *
     * @return the final index of the pivot after partitioning.
     */
    public static int nativePivotLastElement(int[] a, int first, int last) {
        int pivot = a[last];
        int i = last -1;
        for(int j = last - 1; j >= first; j --) {
            if (a[j] > pivot) {
                // swap
                int tmp = a[j];
                a[j] = a[i];
                a[i] = tmp;
                i --;
            }
        }
        // place the pivot in the final position
        a[last] = a[i + 1];
        a[i + 1] = pivot;
        return i + 1;
    }

    /**
     * @return 0 if the median value is a, 1 if the median value is b or 2 if the median value is c.
     */
    public static int chooseMedian(int a, int b, int c) {
        if ((a<=b && b<=c) || (c<=b && b<=a)) return 1;
        if ((a<=c && c<=b) || (b<=c && c<=a)) return 2;
        return 0;
    }
}
