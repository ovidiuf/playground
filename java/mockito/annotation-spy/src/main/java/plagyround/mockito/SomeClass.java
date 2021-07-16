package plagyround.mockito;

public class SomeClass {

    private final ExternalDependency externalDependency;

    public SomeClass(ExternalDependency externalDependency) {
        this.externalDependency = externalDependency;
    }

    public void a() {

        externalDependency.aSupport();
    }

}
