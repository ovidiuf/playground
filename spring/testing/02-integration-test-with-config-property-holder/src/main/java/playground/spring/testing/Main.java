package playground.spring.testing;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception{

        ApplicationContext applicationContext =
                buildApplicationContextAndLoadPropertiesFromKnownConfigurationFiles("playground.spring.testing");




        //
        // verify that the property was loaded in environment
        //
        System.out.println("'playground.spring.testing.name' in environment: " + applicationContext.getEnvironment().getProperty("playground.spring.testing.name"));

        AComponent c = applicationContext.getBean(AComponent.class);

        String name = c.getPropertyConfiguration().getName();

        System.out.println(name);
    }

    private static ApplicationContext buildApplicationContextAndLoadPropertiesFromKnownConfigurationFiles(String basePackage) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.addApplicationListener(new ConfigFileApplicationListener());

        applicationContext.scan(basePackage);

        applicationContext.refresh();

        //
        // trigger property loading
        //

        applicationContext.publishEvent(
                new ApplicationEnvironmentPreparedEvent(
                        new SpringApplication(), new String[0], applicationContext.getEnvironment()));

        //
        // this is an unsuccessful attempt to register a ConfigurationPropertiesBindingPostProcessor that would
        // pull properties from the environment and inject them into @ConfigurationProperties-annotated components
        //
//        applicationContext.addBeanFactoryPostProcessor(new ConfigurationBeanFactoryMetadata());
//        applicationContext.register(ConfigurationBeanFactoryMetadataProvider.class);
//        final ConfigurationPropertiesBindingPostProcessor cpbp = new ConfigurationPropertiesBindingPostProcessor();
//        cpbp.setApplicationContext(applicationContext);
//        cpbp.afterPropertiesSet();
//        final BeanFactoryPostProcessor bfpp = beanFactory -> beanFactory.addBeanPostProcessor(cpbp);
//        applicationContext.addBeanFactoryPostProcessor(bfpp);

        return applicationContext;
    }

}
