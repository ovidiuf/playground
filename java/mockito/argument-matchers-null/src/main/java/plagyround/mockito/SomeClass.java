package plagyround.mockito;

public class SomeClass {

    private final ExternalDependency externalDependency;

    public SomeClass(ExternalDependency externalDependency) {
        this.externalDependency = externalDependency;
    }

    public Result someMethod(String s) {
        return externalDependency.process(s);
    }
}
