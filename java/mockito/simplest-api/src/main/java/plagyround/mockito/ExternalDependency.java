package plagyround.mockito;

import java.util.ArrayList;
import java.util.List;

public class ExternalDependency {

    private int i;
    private List<String> content;

    public ExternalDependency() {
        this.content = new ArrayList<>();
        reset();
    }

    public String readNextLine() throws Exception {
        if (i < content.size()) {
            return content.get(i++);
        }
        return null;
    }

    public void writeLine(String s) throws Exception {
        content.add(s);
    }

    public void reset() {
        i = 0;
    }
}
