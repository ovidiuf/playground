package io.novaordis.playground.algorithms.insertionsort;

public class Main {

    public static void main(String[] args) {

        Configuration c = new Configuration(args);

        int[] unsorted = c.getArray();

        int[] sorted = insertionSort(unsorted);

        for(int i = 0; i < sorted.length; i ++) {

            System.out.print(sorted[i]);

            if (i < sorted.length - 1) {

                System.out.print(' ');
            }
        }
        System.out.println();
    }

    public static int[] insertionSort(int[] A) {

        for(int j = 1; j < A.length; j ++) {

            int key = A[j];

            int i = j - 1;

            while(i > -1 && A[i] > key) {

                A[i + 1] = A[i];

                i = i - 1;
            }

            A[i + 1] = key;
        }

        return A;
    }
}
