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

    /**
     * On lines longer than 20 characters, throws InvalidArgumentException
     */
    public void writeLine(String s) throws Exception {
        if (s != null && s.length() > 20) throw new IllegalAccessException("line too long");
        content.add(s);
    }

    public boolean containsLine(String s) {
        for(String line: content) {
            if (line.equals(s)) return true;
        }
        return false;
    }

    public void reset() {
        i = 0;
    }

    public String config(String system, String subSystem) {
        if ("thread-pool".equals(system)) {
            if ("autoscaler".equals(subSystem)) {
                return "10";
            }
        }
        return "N/A";
    }
}
