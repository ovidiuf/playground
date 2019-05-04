package playground.b;

import playground.a.A;

public class Main {

    public static void main(String[] args) {

        System.out.println("this is b, and it depends on " + new A());
    }
}
