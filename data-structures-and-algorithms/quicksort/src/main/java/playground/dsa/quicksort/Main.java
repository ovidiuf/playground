package playground.dsa.quicksort;

import playground.dsa.common.DSA;

public class Main {

    public static void main(String[] args) {

        int[] a;

        if (args.length > 0) {

            if ("test".equals(args[0])) {

                test();
                return;

            }

            a = DSA.fromCommandLine(args);
        }
        else {

            a = DSA.unsortedNonNegativeArray(30, 100);
        }

        DSA.printArray(a);

        Quicksort quicksort = new Quicksort();

        quicksort.sort(a);

        DSA.printArray(a);
    }

    private static void test() {

        DSA.test(100000, 1000, 50, new Quicksort(), false);
    }
}
