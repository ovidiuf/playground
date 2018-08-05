package playground.dsa.common;

import java.util.Random;

public class DSA {

    private DSA() {
    }

    public static int[] unsortedArray(int size, int maxValue) {

        Random random = new Random();

        int[] result = new int[size];

        for(int i = 0; i < result.length; i ++) {

            result[i] = random.nextInt(maxValue);
        }

        return result;
    }

    public static void printArray(int[] a) {

        for(int i = 0; i < a.length; i ++) {

            System.out.print(a[i]);

            if (i < a.length - 1) {

                System.out.print(' ');
            }
        }

        System.out.println();
    }
}
