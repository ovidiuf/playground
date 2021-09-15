package plagyround.generics;

public class SomeClass<T> {
    public T doSomething(T t) {
      System.out.println(t);
      return t;
    }
    public <V> V doSomethingElse(V v) {
      System.out.println(v);
      return v;
    }
}
