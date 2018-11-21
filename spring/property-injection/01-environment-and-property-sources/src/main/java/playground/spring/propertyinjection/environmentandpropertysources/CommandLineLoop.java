package playground.spring.propertyinjection.environmentandpropertysources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineLoop {

    private MyPropertyConfiguration propertyConfiguration;

    @Autowired
    private CommandLineLoop(MyPropertyConfiguration propertyConfiguration) {

        this.propertyConfiguration = propertyConfiguration;
    }

    void run(String... args) {

        System.out.println("");
        System.out.println(" color: " + propertyConfiguration.getColor());
        System.out.println("  size: " + propertyConfiguration.getSize());
        System.out.println("");
    }
}
