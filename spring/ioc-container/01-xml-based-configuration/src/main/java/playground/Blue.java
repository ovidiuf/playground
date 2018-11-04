package playground;

@SuppressWarnings("WeakerAccess")
public class Blue {

    private Red red;

    private String payload;

    public Blue(Red red, String payload) {

        this.red = red;
        this.payload = payload;
    }


    public void run() {

        System.out.println(this + " running ...");

        red.run();
    }

    @Override
    public String toString() {

        return "Blue[" + payload + "]";
    }
}
