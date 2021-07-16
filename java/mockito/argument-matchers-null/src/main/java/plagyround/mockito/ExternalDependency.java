package plagyround.mockito;

public class ExternalDependency {

    public Result process(String s) {
        return new Result(s);
    }
}
