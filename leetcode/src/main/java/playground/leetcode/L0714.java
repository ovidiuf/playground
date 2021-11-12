package playground.leetcode;

import java.io.BufferedReader;
import java.io.FileReader;

public class L0714 {
    public void run() throws Exception {
//        int[] prices = {1,3,2,8,4,9};
//        int fee = 2;
//        int[] prices = {3,5,1};
//        int fee = 1;
//        int[] prices = {1,3,7,5,10,3};
//        int fee = 3;
//        int[] prices = {};
//        int fee = 6806;
        int[] prices = loadFromFile();
        int fee = 6806;
        System.out.println("prices: " + prices.length + ", first: " + prices[0] + ", last: " + prices[prices.length - 1] + ", fee: " + fee);
        System.out.println(new Solution0714().maxProfit(prices, fee));
    }

    private int[] loadFromFile() throws Exception {
        String fileName = "./data/L0714.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine().trim();
            String[] tok = line.split(",");
            int[] result = new int[tok.length];
            int i = 0;
            for(String s: tok) {
                result[i++] = Integer.parseInt(s);
            }
            return result;
        }
    }
}

class Solution0714 {
    private int[] prices;
    private double fee;
    private Double [][] FPC; // 0 means don't own stock, 1 means own stock
    public int maxProfit(int[] prices, int fee) {
        this.fee = ((float)fee)/2;
        this.prices = prices;
        this.FPC = new Double[prices.length][2];
        return (int) forwardProfit(0, false);
    }

    private double forwardProfit(int day, boolean ownStock) {
        // base case
        if (day == prices.length) {
            return 0;
        }
        double pOnSale = Integer.MIN_VALUE, pOnBuy = Integer.MIN_VALUE, pOnNothing;
        if (ownStock) {
            // sell - first check if to see whether we don't have that result already
            if (day < prices.length - 1 && FPC[day + 1][0] != null) {
                pOnSale = FPC[day + 1][0];
            }
            else {
                pOnSale = forwardProfit(day + 1, false);
            }
            pOnSale = pOnSale - fee + prices[day];
        }
        else {
            // buy - first check if to see whether we don't have that result already
            if (day < prices.length - 1 && FPC[day + 1][1] != null) {
                pOnBuy = FPC[day + 1][1];
            }
            else {
                pOnBuy = forwardProfit(day + 1, true);
            }
            pOnBuy = pOnBuy - fee - prices[day];
        }
        if (day < prices.length - 1 && FPC[day + 1][ownStock ? 1 : 0] != null) {
            pOnNothing = FPC[day + 1][ownStock ? 1 : 0];
        }
        else {
            pOnNothing = forwardProfit(day + 1, ownStock);
        }

        double result = Math.max(Math.max(pOnSale, pOnBuy), pOnNothing);
        //cache the result
        FPC[day][ownStock ? 1 : 0] = result;
        return result;
    }
}
