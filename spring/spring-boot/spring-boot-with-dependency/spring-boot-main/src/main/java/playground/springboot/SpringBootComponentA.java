package playground.springboot;

import org.springframework.stereotype.Component;

@Component
public class SpringBootComponentA {

    public void run() {

        System.out.println(this + " is running");
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
