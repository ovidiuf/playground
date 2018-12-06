package playground.spring.testing.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import playground.spring.testing.AComponent;
import playground.spring.testing.AComponentPropertyConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        AComponent.class,
        AComponentPropertyConfiguration.class,
        PropertyAwareTestContextConfiguration.class},
        initializers = PropertyAwareTestContextInitializer.class)
public class AComponentIntegrationTests {

    @Autowired
    private AComponent aComponent;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Test
    public void realContextInjectsComponent() {

        String valueInEnvironment = applicationContext.getEnvironment().getProperty("playground.spring.testing.name");

        assertEquals("red", valueInEnvironment);

        assertNotNull(aComponent);

        String valueFromComponent = aComponent.getPropertyConfiguration().getName();

        assertEquals("red", valueFromComponent);
    }
}
