package playground.spring.testing;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("light")
public class BComponent implements WeightAwareComponent {

    @Override
    public String toString() {

        return "light";
    }

}
