package playground.dsa.maximumSubarrayProblem;

public class MaxSubarray {

    public int[] recursiveMaxSubarray(int[] a, int s, int e) {

        //
        // detect the bottom and exit the recurrence
        //

        if (s + 1 == e) {

            //
            // one element array, the trivial case of the maximum subarray problem
            //

            return new int[] {s, e, a[s]};
        }

        //
        // we're at an intermediary level in the recurrence
        //


        int c = s + (e - s)/2;

        //
        // Find the maximum subarray in the left half
        //

        int[] left = recursiveMaxSubarray(a, s, c);

        //
        // Find the maximum subarray in the right half
        //

        int[] right = recursiveMaxSubarray(a, c, e);

        //
        // The contiguous maximum subarray can be either in the left half,
        // right half or straddle the center. Luckily, we can figure out the
        // maximum subarray straddling the center in O(n)
        //

        int[] center = determineMaxSubarrayStraddlingTheCenter(a, s, e, c);

        //
        // we have three candidates, pick the one with the biggest sum. This method is O(1)
        //

        int[] max = determineMax(left, right, center);

        return max;
    }


    /**
     * @param c the dividing line is at the left of c.
     *
     * @return int[3] {maxStart, maxEnd, max}
     */
    private int[] determineMaxSubarrayStraddlingTheCenter(int[] a, int s, int e, int c) {

        //
        // expand left
        //

        int l = c - 1;
        int sumLeft = 0;
        int maxSumLeft = Integer.MIN_VALUE;
        int maxStart = -1;
        while(l >= s) {
            sumLeft += a[l];
            if (sumLeft > maxSumLeft) {
                maxSumLeft = sumLeft;
                maxStart = l;
            }
            l --;
        }

        //
        // expand right
        //

        int r = c;
        int sumRight = 0;
        int maxSumRight = Integer.MIN_VALUE;
        int maxEnd = -1;
        while(r < e) {
            sumRight += a[r];
            if (sumRight > maxSumRight) {
                maxSumRight = sumRight;
                maxEnd = r + 1;
            }
            r ++;
        }

        //
        // combine local maximums
        //

        return new int[] {maxStart, maxEnd, maxSumLeft + maxSumRight};
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
