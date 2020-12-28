package playground.gcLogging;

public class Main {

    private static final int MiB = 1024 * 1024;

    int bufferSize = 10;
    int mibs = 400;

    private final byte[][] storage = new byte[bufferSize][];

    public static void main(String[] args) throws Exception {
        new Main().allocateAndDeallocate();
    }

    private void allocateAndDeallocate() throws Exception {

        int i = 0;

        while(true) {

            int j = i;
            storage[i] = null;
            i = (i + 1) % bufferSize;
            storage[i] = new byte[mibs * MiB];
            System.out.println("deallocated slot " + j + ", allocated " + mibs + " MiB in slot " + i);
            Thread.sleep(500L);

        }
    }
}
