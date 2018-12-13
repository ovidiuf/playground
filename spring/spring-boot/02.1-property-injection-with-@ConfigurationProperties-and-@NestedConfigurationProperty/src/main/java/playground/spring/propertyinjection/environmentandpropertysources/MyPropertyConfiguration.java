package playground.spring.propertyinjection.environmentandpropertysources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "playground.spring.pi")
public class MyPropertyConfiguration {

    private int size;

    @NestedConfigurationProperty
    private NestedConfiguration stuff;

    public void setSize(int i) {

        this.size = i;
    }

    public int getSize() {

        return size;
    }

    public NestedConfiguration getStuff() {

        return stuff;
    }

    public void setStuff(NestedConfiguration ncp) {

        this.stuff = ncp;
    }

}
