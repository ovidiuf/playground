package playground.springboot;

import org.springframework.stereotype.Component;

@Component
public class MainApplicationComponent {

    public void run() {

        System.out.println("#");
        System.out.println("# " + this + " is running");
        System.out.println("#");
    }
}
