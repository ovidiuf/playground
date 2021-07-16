package plagyround.mockito;

public class SomeClass {

    private final ExternalDependency externalDependency;

    public SomeClass(ExternalDependency externalDependency) {
        this.externalDependency = externalDependency;
    }

    public void write(String s) throws Exception {
        externalDependency.writeLine(s.toLowerCase());
    }

    public String read() throws Exception {
        return externalDependency.readNextLine();
    }

    public boolean lineExists(String line) {
        return externalDependency.containsLine(line);
    }

    public String dumpConfig(String system, String subSystem) {
        return  externalDependency.config(system, subSystem);
    }
}
