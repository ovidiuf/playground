package playground.spring.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AComponent {

    private BComponent bComponent;

    @Autowired
    public AComponent(BComponent bComponent) {

        this.bComponent = bComponent;
    }

    public BComponent getDependency() {

        return bComponent;
    }
}
