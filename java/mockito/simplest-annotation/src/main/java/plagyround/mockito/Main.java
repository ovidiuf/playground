package plagyround.mockito;

public class Main {

    public static void main(String[] args) throws Exception {
        ExternalDependency externalDependency = new ExternalDependency();
        SomeClass c = new SomeClass(externalDependency);
        c.write("Something");
        System.out.println(c.read());
    }
}
