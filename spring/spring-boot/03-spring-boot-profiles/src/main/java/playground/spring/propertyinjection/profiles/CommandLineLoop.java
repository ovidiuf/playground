package playground.spring.propertyinjection.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineLoop {

    private SimplePropertyHolder props;

    @Autowired
    private CommandLineLoop(SimplePropertyHolder props) {

        this.props = props;
    }

    void run(String... args) {

        System.out.println("");
        System.out.println("  color: " + props.getColor());
        System.out.println("");
    }
}
