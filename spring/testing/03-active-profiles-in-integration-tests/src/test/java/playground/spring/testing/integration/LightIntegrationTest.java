package playground.spring.testing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import playground.spring.testing.AComponent;
import playground.spring.testing.BComponent;
import playground.spring.testing.WeightAwareComponent;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AComponent.class, BComponent.class})
@ActiveProfiles("light")
public class LightIntegrationTest {

    @Autowired
    private WeightAwareComponent weightAwareComponent;

    @Test
    public void realContextInjectsComponent() {

        assertNotNull(weightAwareComponent);

        System.out.println("got " + weightAwareComponent);
    }
}
