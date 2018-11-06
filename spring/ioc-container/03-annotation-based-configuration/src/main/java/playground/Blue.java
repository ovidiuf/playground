package playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
public class Blue {

    private Red red;

    private String payload;

    @Autowired
    public Blue(Red red) {

        this(red, "pale");
    }

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
