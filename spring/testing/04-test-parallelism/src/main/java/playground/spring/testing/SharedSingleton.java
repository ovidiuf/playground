package playground.spring.testing;

import org.springframework.stereotype.Component;

@Component
public class SharedSingleton {

    private String color;

    public void setColor(String color) {

        this.color = color;
    }

    @Override
    public String toString() {

        return "SharedSingleton[" + Integer.toHexString(System.identityHashCode(this)) + "](" + color + ")";
    }

}
