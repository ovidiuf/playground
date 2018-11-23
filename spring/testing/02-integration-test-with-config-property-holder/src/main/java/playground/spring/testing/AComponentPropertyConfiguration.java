package playground.spring.testing;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "playground.spring.testing")
public class AComponentPropertyConfiguration {

    private String name;

    public AComponentPropertyConfiguration() {

        this.name = "blue";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
