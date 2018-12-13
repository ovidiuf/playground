package playground.spring.propertyinjection.environmentandpropertysources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
@ConfigurationProperties(prefix = "playground.spring.pi")
public class CommandLineLoop {

    public static final int DEFAULT_WEIGHT = 50;

    private int weight = DEFAULT_WEIGHT;

    private MyPropertyConfiguration propertyConfiguration;

    @Autowired
    private CommandLineLoop(MyPropertyConfiguration propertyConfiguration) {

        this.propertyConfiguration = propertyConfiguration;
    }

    public void setWeight(int weight) {

        this.weight = weight;
    }

    void run(String... args) {

        System.out.println("");
        System.out.println("   size: " + propertyConfiguration.getSize());
        System.out.println("   stuff.weight: " + propertyConfiguration.getStuff().getWeight());
        System.out.println("");
    }
}
