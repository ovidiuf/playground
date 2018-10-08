package io.novaordis.playground.misc.fish;

public class Main {

    public static void main(String[] args) {


        //
        // 0:  0 - 1 year old
        // 1:  1 - 2 year old
        // 2:  2 - 3 year old
        // 3:  3 - 4 year old
        // 4:  4 - 5 year old
        // 5:  5 - 6 year old
        // 6:  6 - 7 year old
        // ...
        // 99: 99 - 100 year old

        int[] model = new int[100];

        initialize(model);

        displayModel(model);

        System.out.println("\n\n");

        evolveModel(model, 5);

        displayModel(model);
    }

    private static void initialize(int[] model) {

        for(int i = 0; i < 100; i ++) {

            model[i] = 100 - i;
        }
    }

    private static void evolveModel(int[] model, int years) {

        for(int i = 0; i < years; i ++) {

            evolveOverOneYear(model);
            displayModel(model);


        }
    }

    private static void evolveOverOneYear(int[] model) {

        displayModel(model);

        //
        // die
        //

        int newFish01 = (int)((double)model[0] * 0.01);

        int newFish12 = (int)((double)model[1] * 0.1);

        int[] newRest = new int[98];

        for(int i = 0; i < 98; i ++) {

            newRest[i] = (int)((double)model[i + 2] * 0.75);
        }

        //
        // update
        //

        model[0] = newFish01;
        model[1] = newFish12;
        for(int i = 0; i < 98; i ++) {

            model[i + 2] = newRest[i];
        }

        //
        // spawn
        //

        int newBorn = 0;

        for(int i = 5; i < 100; i ++) {

            newBorn += model[i] * 200;
        }

        //
        // age
        //

        for(int i = 1; i < 100; i ++) {

            model[i] = model[i - 1];
        }

        model[0] = newBorn;

        displayModel(model);

        System.out.println("\n\n");

    }


    private static void displayModel(int[] model) {

        for(int i = 0; i < model.length; i ++) {

            System.out.print(model[i] + " ");
        }

        System.out.println();

    }

}
