package playground.spring.testing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import playground.spring.testing.AComponent;
import playground.spring.testing.AComponentPropertyConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        AComponent.class,
        AComponentPropertyConfiguration.class})
public class AComponentIntegrationTests {

    @Autowired
    private AComponent aComponent;

    @Test
    public void realContextInjectsComponent() {

        assertNotNull(aComponent);

        String name = aComponent.getPropertyConfiguration().getName();

        System.out.println(name);

        assertEquals("yellow", name);
    }
}
