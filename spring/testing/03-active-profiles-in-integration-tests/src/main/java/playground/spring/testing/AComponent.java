package playground.spring.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("heavy")
public class AComponent implements WeightAwareComponent {

    @Override
    public String toString() {

        return "heavy";
    }
}
