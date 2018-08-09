package playground.dsa.maximumSubarrayProblem;

import playground.dsa.common.DSA;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {

            MaxProfit algorithm = new MaxProfit();

            // we generate test data

            for(int i = 0; i < 10000; i ++) {

                int[] prices = DSA.unsortedNonNegativeArray(100, 500);


                int[] recursiveResult = algorithm.recursive(prices);
                int[] bruteForceResult = algorithm.bruteForce(prices);

                //
                // we should make the same profit, maximum
                //

                if (recursiveResult[2] == bruteForceResult[2]) {

                    // verify that is indeed so

                    if (prices[recursiveResult[1]] - prices[recursiveResult[0]] == prices[bruteForceResult[1]] - prices[bruteForceResult[0]]) {

                        System.out.print('.');
                    }
                    else {

                        System.out.println("error");
                        return;
                    }


//                    if (recursiveResult[0] == bruteForceResult[0] && recursiveResult[1] == bruteForceResult[1]) {
//
//                        // same solution
//                        System.out.print('.');
//
//                    }
//                    else {
//
//                        System.out.println("\n algorithms found same profit but different sequences:");
//                        DSA.printArray(prices);
//                        System.out.println("recursive buy " + recursiveResult[0] + ", sell " + recursiveResult[1] + ", brute force buy " +  bruteForceResult[0] + ", sell " +  bruteForceResult[1]);
//                    }

                }
                else {

                    System.out.println("\nfound error:");
                    DSA.printArray(prices);
                    System.out.println("recursive buy " + recursiveResult[0]  + ", sell " + recursiveResult[1] + ", make " + recursiveResult[2]);
                    System.out.println("bf        buy " + bruteForceResult[0]  + ", sell " + bruteForceResult[1] + ", make " + bruteForceResult[2]);
                    return;
                }

                System.out.println();
            }
        }
        else {

            // we read the data from command line

            int[] price = DSA.fromCommandLine(args);

            DSA.printArray(price);

            int[] r1 = new MaxProfit().recursive(price);

            System.out.println("recursive:   maximum profit of " + r1[2] + " is obtained by buying on day " + r1[0] + " and selling on day " + r1[1]);

            int[] r2 = new MaxProfit().bruteForce(price);

            System.out.println("brute force: maximum profit of " + r2[2] + " is obtained by buying on day " + r2[0] + " and selling on day " + r2[1]);
        }
    }
}
