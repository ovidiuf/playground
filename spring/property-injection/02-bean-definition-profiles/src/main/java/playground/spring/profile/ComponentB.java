package playground.spring.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("blue")
public class ComponentB implements ColorAware {

    @Override
    public String toString() {

        return "blue";
    }
}
