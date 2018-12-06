package playground.spring.testing;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "playground.spring.testing")
public class AComponentPropertyConfiguration {

    private String name;

    public AComponentPropertyConfiguration() {

        //
        // This is the default value and it should be overwritten by the value maintained in
        // src/test/resources/application.yml
        //

        this.name = "blue";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
