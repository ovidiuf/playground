package playground.spring.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("red")
public class ComponentA implements ColorAware {

    @Override
    public String toString() {

        return "red";
    }
}
