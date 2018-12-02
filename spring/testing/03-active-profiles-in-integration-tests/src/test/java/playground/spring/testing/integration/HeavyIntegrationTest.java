package playground.spring.testing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import playground.spring.testing.AComponent;
import playground.spring.testing.AComponentPropertyConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@ActiveProfiles("heavy")
@ContextConfiguration(classes = {
        AComponent.class,
        AComponentPropertyConfiguration.class})
public class HeavyIntegrationTest {

    @Autowired
    private AComponent aComponent;

    @Test
    public void realContextInjectsComponent() {

        assertNotNull(aComponent);

        String name = aComponent.getPropertyConfiguration().getName();

        System.out.println("running a heavy integration test on " + name);

        fail("heavy integration testing failed");
    }
}
