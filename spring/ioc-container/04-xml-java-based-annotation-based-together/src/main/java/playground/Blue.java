package playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
public class Blue {

    private Red red;

    @Autowired
    public Blue(Red red) {

        this.red = red;
    }

    public String run() {

        return this + " -> " + red.run();
    }

    @Override
    public String toString() {

        return "Blue[]";
    }
}
