package playground;

@SuppressWarnings("WeakerAccess")
public class Red {

    private String payload;

    public Red(String payload) {

        this.payload = payload;
    }

    public void run() {

        System.out.println(this + " running ...");
    }

    @Override
    public String toString() {

        return "Red[" + payload + "]";
    }
}
