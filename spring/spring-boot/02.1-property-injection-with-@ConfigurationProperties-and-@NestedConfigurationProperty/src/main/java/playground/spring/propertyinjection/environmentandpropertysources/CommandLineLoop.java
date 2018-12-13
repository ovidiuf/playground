package playground.spring.propertyinjection.environmentandpropertysources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
@ConfigurationProperties(prefix = "playground.spring.pi")
public class CommandLineLoop {

    public static final int DEFAULT_WEIGHT = 50;

    private int weight = DEFAULT_WEIGHT;

    private MyPropertyConfiguration propertyConfiguration;

    private Environment environment;

    @Autowired
    private CommandLineLoop(MyPropertyConfiguration propertyConfiguration, Environment environment) {

        this.propertyConfiguration = propertyConfiguration;
        this.environment = environment;
    }

    public void setWeight(int weight) {

        this.weight = weight;
    }

    void run(String... args) {

        String s = environment.getProperty("playground.spring.pi.stuff.weight");

        assert "55".equals(s);

        System.out.println("");
        System.out.println("   size: " + propertyConfiguration.getSize());
        System.out.println("   stuff.weight: " + propertyConfiguration.getStuff().getWeight());
        System.out.println("");



    }
}
