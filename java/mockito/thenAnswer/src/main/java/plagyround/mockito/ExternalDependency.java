package plagyround.mockito;

public class ExternalDependency {

    public Result process(String s, long l) {
        return new Result(s, l);
    }
}
