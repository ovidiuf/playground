package plagyround.generics;

public class Main {

    public static void main(String[] args) {

       SomeClass<String> scs = new SomeClass<>();
       String s = scs.doSomething("something");

       //scs.doSomething(new Object());

       SomeClass<Integer> sci = new SomeClass<>();
       Integer i = sci.doSomething(1);

       Double d = sci.doSomethingElse(1.0d);
    }
}
