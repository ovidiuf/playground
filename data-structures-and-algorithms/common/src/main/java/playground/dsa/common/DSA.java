package playground.dsa.common;

import java.util.*;

public class DSA {

    private DSA() {
    }

    @SuppressWarnings("WeakerAccess")
    public static int[] unsortedArray(int size, int maxValue) {

        Random random = new Random();

        int[] result = new int[size];

        for(int i = 0; i < result.length; i ++) {

            result[i] = random.nextInt(maxValue);
        }

        return result;
    }

    public static int[] sortedArray(int size, int maxValue) {

        int[] a = unsortedArray(size, maxValue);

        List<Integer> list = new ArrayList<>();

        for(int i: a) {

            list.add(i);
        }

        Collections.sort(list);

        for(int i = 0; i < a.length; i ++) {

            a[i] = list.get(i);
        }

        return a;
    }

    @SuppressWarnings("WeakerAccess")
    public static void printArray(int[] a) {

        for(int i = 0; i < a.length; i ++) {

            System.out.print(a[i]);

            if (i < a.length - 1) {

                System.out.print(' ');
            }
        }

        System.out.println();
    }

    public static void test(int arrays, int maxSize, int maxValue, SortingAlgorithm algorithm, boolean printSortedArray) {

        Random random = new Random();

        for(int i = 0; i < arrays; i ++) {

            int size;

            while((size = random.nextInt(maxSize + 1)) == 0) {};

            int[] a = unsortedArray(size, maxValue);

            //
            // make a copy in case we sort in place
            //

            int[] original = new int[a.length];
            System.arraycopy(a, 0, original, 0, a.length);
            int[] copyToSortWithLibraryMethod = new int[a.length];
            System.arraycopy(a, 0, copyToSortWithLibraryMethod, 0, a.length);

            int[] sorted = algorithm.sort(a);

            if (printSortedArray) {

                printArray(sorted);
            }

            Arrays.sort(copyToSortWithLibraryMethod);

            for(int j = 0; j < sorted.length; j ++) {

                if (sorted[j] != copyToSortWithLibraryMethod[j]) {

                    System.out.println("found incorrectly sorted array");
                    System.out.print("original:         "); DSA.printArray(original);
                    System.out.print("sorted:           "); DSA.printArray(sorted);
                    System.out.print("correctly sorted: "); DSA.printArray(copyToSortWithLibraryMethod);
                    return;
                }
            }
        }

        System.out.println(arrays + " arrays of maximum " + maxSize + " elements correctly sorted");
    }
}
