package io.novaordis.playground.lombok.Builder;

public class Main {

    public static void main(String[] args) {

        Something s = Something.builder().id(1L).name("blah").build();

        System.out.println(s.toString());
    }
}
