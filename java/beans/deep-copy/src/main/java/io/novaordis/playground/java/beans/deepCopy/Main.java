package io.novaordis.playground.java.beans.deepCopy;

public class Main {

    public static void main(String[] args) {

        SomethingElse se = new SomethingElse("blue");
        Something s = new Something(1, "blah", se);

        Something s2 = s.clone();
        System.out.println("s == s2: " + (s == s2));
        System.out.println("s.equals(s2): " + s.equals(s2));
        System.out.println("s class: " + s.getClass() + ", s2 class: " + s2.getClass());

        System.out.println("s:  " + s);
        System.out.println("s2: " + s2);


        se.setColor("red");

        System.out.println("s:  " + s);
        System.out.println("s2: " + s2);


    }
}
