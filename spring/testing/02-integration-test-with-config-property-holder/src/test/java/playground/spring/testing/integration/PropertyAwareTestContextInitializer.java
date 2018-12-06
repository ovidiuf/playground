package playground.spring.testing.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class PropertyAwareTestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        //
        // expose ./src/test/resources/application.yml as a property source
        //

        new ConfigFileApplicationListener().postProcessEnvironment(
                applicationContext.getEnvironment(), new SpringApplication());

        //
        // register a BeanPostProcessor that loads the property values from the environment into
        // @ConfigurationProperties classes
        //

        applicationContext.addBeanFactoryPostProcessor(
                beanFactory -> beanFactory.addBeanPostProcessor(
                        applicationContext.getBean(PropertyAwareTestContextConfiguration.class).
                                getConfigurationPropertiesBindingPostProcessor()));
    }
}
