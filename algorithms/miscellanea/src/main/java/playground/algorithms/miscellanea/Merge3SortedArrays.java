package playground.algorithms.miscellanea;

/**
 * Implement a function to merge 3 sorted integer arrays. The output should be another sorted integer array consisting
 * of all integers from the 3 input arrays. Input arrays may have duplicates, but the output array should not have
 * any duplicates.
 *
 * Requirements:
 * 1. Linear time
 * 2. Zero memory overhead, except the output array
 */
public class Merge3SortedArrays {

    public void run() throws Exception {
        int[] a = {-10, 0, 1, 1, 5};
        int[] b = {-1, 0, 0, 1};
        int[] c = {-1, -1, 0};
        Result r = merge(a, b, c);
        for(int i = 0; i < r.l; i ++) {
            System.out.print(r.m[i] + " ");
        }
        System.out.println();
    }

    private static Result merge(int[] a, int[] b, int[] c) {
        int[] m = new int[a.length + b.length + c.length];
        int ml = 0;
        int i = 0, j = 0, k = 0, p = 0;
        while(true)
        {
            int crta = i < a.length ? a[i] : Integer.MAX_VALUE;
            int crtb = j < b.length ? b[j] : Integer.MAX_VALUE;
            int crtc = p < c.length ? c[k] : Integer.MAX_VALUE;
            int min = min(crta, crtb, crtc);
            if (min == Integer.MAX_VALUE) {
                break;
            }
            if (min == crta) {
                i++;
            }
            if (min == crtb) {
                j++;
            }
            if (min == crtc) {
                k++;
            }
            if (p == 0) {
                m[p++] = min;
                ml ++;
            }
            else if (m[p - 1] != min) {
                m[p++] = min;
                ml ++;
            }
        }
        return new Result(m, ml);
    }

    private static class Result {
        public int[] m;
        public int l;
        public Result(int[] m, int l) {
            this.m = m;
            this.l = l;
        }
    }
    private static int min(int a, int b, int c) {
        int min = a;
        if (b < a) {
            min = b;
        }
        if (c < min) {
            min = c;
        }
        return min;
    }
}
