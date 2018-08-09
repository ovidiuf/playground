package playground.dsa.maximumSubarrayProblem;

public class MaxProfit {

    /**
     * @return an int[3] with { buyDateIndex, sellDateIndex, profit}
     */
    public int[] bruteForce(int[] price) {

        int maxProfit = Integer.MIN_VALUE;
        int b = -1;
        int s = -1;

        for(int i = 0; i < price.length; i ++) {

            for(int j = i + 1; j < price.length; j ++) {


                int profit = price[j] - price[i];

                if (profit > maxProfit) {

                    maxProfit = profit;
                    b = i;
                    s = j;
                }
            }
        }

        return new int[] {b, s, maxProfit};
    }

    public int[] priceToDiff(int[] price) {

        int[] diff = new int[price.length - 1];

        int j = 0;

        for(int i = 1; i < price.length; i ++) {

            diff[j ++] = price[i] - price[i - 1];
        }

        return diff;
    }

    public int[] recursive(int[] price) {

        int[] diff = priceToDiff(price);


        int[] max = new MaxSubarray().recursiveMaxSubarray(diff, 0, diff.length);

        int start = max[0];
        int end = max[1];

        return new int[] {start, end, price[end] - price[start]};

    }
}
