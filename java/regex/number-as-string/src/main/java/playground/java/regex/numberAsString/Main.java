package playground.java.regex.numberAsString;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {

            System.err.println("provide a string");
            return;
        }


        System.out.println(args[0] + " is " + (NumberAsString.isNumber(args[0]) ? "" : "NOT ")  + "a number");
    }
}
