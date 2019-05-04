package playground.c;

import playground.b.B;

public class Main {

    public static void main(String[] args) {

        System.out.println("this is " + new C() + ", and we depend on " + new B());
    }
}
