package playground.dsa.maximumSubarrayProblem;

public class MaxSubarray {

    public int[] recursiveMaxSubarray(int[] a, int s, int e) {

         if (s + 1 == e) {

            // bottom

            return new int[] {s, e, a[s]};
        }

        int c = s + (e - s)/2;

        int[] left = recursiveMaxSubarray(a, s, c);
        int[] right = recursiveMaxSubarray(a, c, e);
        int[] center = determineMaxSubarraySpanningCenter(a, s, e, c);

        int[] max = determineMax(left, right, center);

        return max;
    }


    /**
     * @param c the dividing line is at the left of c.
     *
     * @return int[3] {maxStart, maxEnd, max}
     */
    private int[] determineMaxSubarraySpanningCenter(int[] a, int s, int e, int c) {

        int ms = -1;
        int me = c;

        // expand left
        int l = c - 1;
        int sumLeft = 0;
        int sumLeftMax = Integer.MIN_VALUE;

        while(l >= s) {

            sumLeft += a[l];

            if (sumLeft > sumLeftMax) {

                sumLeftMax = sumLeft;
                ms = l;
            }

            l --;
        }

        // expand right
        int r = c;
        int sumRight = 0;
        int sumRightMax = Integer.MIN_VALUE;

        while(r < e) {

            sumRight += a[r];

            if (sumRight > sumRightMax) {

                sumRightMax = sumRight;
                me = r + 1;
            }

            r ++;
        }

        return new int[] {ms, me, sumLeftMax + sumRightMax};
    }

    private int[] determineMax(int[] a, int[] b, int[] c) {

        if (a[2] > b[2]) {

            // max is a or c

            if (a[2] > c[2]) {

                // max is a
                return a;

            }
            else {

                // max is c
                return c;
            }
        }
        else {

            // max is b or c

            if (b[2] > c[2]) {

                // max is b
                return b;
            }
            else {

                // max is c
                return c;
            }
        }
    }

}
