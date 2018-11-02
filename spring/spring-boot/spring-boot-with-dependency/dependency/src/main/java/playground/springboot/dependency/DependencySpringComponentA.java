package playground.springboot.dependency;

import org.springframework.stereotype.Component;

@Component
public class DependencySpringComponentA {

    public void run() {

        System.out.println(this + " is running");
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
