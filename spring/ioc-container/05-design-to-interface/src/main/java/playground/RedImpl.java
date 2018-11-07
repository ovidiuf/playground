package playground;

import org.springframework.stereotype.Component;

@Component
public class RedImpl implements Red {

    public String run() {

        return this.toString();
    }

    @Override
    public String toString() {

        return "Red[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}