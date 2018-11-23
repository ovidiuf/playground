package playground.spring.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AComponent {

    private AComponentPropertyConfiguration propertyConfiguration;

    @Autowired
    public AComponent(AComponentPropertyConfiguration propertyConfiguration) {

        this.propertyConfiguration = propertyConfiguration;
    }

    public AComponentPropertyConfiguration getPropertyConfiguration() {

        return propertyConfiguration;
    }
}
