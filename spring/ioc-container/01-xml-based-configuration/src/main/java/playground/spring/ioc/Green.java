package playground.spring.ioc;

@SuppressWarnings("WeakerAccess")
public class Green {

    private Red red;

    private String payload;

    public Green(String payload) {

        this.payload = payload;
    }

    public void setRed(Red red) {

        this.red = red;
    }

    public void run() {

        System.out.println(this + " running ...");

        if (red == null) {

            System.out.println(this + ": red dependency not injected");
        }
        else {

            red.run();
        }
    }

    @Override
    public String toString() {

        return "Green[" + payload + "]";
    }
}
