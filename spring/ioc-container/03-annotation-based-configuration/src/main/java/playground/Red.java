package playground;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
public class Red {

    private String payload;

    public Red(@Value("bright") String payload) {

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