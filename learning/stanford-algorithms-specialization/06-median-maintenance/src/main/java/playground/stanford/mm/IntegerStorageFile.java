package playground.stanford.mm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class IntegerStorageFile {

    private final BufferedReader br;

    public IntegerStorageFile(String filePath) throws Exception {
        File f = new File(filePath);
        br = new BufferedReader(new FileReader(f));
    }

    /**
     * @return null if no more ints are found in the file. Will assume the integers are positive and fail with
     * IllegalStateException if it finds a negative integer.
     */
    public Integer nextInt() throws Exception {
        String line = br.readLine();
        if (line == null) {
            return null;
        }
        line = line.trim();
        int i = Integer.parseInt(line);
        if (i < 0) {
            throw new IllegalStateException("found negative integer: " + i);
        }
        return i;
    }

    public void close() throws Exception {
        br.close();
    }
}
