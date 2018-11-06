package playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
public class Green {

    private Red red;

    private String payload;

    public Green() {

        this("fresh");
    }


    public Green(String payload) {

        this.payload = payload;
    }

    @Autowired
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
