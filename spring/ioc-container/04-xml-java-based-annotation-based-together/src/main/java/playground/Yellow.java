package playground;

public class Yellow {

    private Red red;

    private String payload;

    public Yellow(Red red, String payload) {

        this.red = red;
        this.payload = payload;
    }

    public void run() {

        System.out.println(this + " running ...");

        red.run();
    }

    @Override
    public String toString() {

        return "Yellow[" + payload + "]";
    }

}
