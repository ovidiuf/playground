package playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlueImpl implements Blue {

    private Red red;

    @Autowired
    public BlueImpl(Red red) {

        this.red = red;
    }

    public String run() {

        return this + " -> " + red.run();
    }

    @Override
    public String toString() {

        return "Blue[" + Integer.toHexString(System.identityHashCode(this))  + "]";
    }
}