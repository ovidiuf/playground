package playground.spring.testing.integration;

import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyAwareTestContextConfiguration {

    @Bean
    public ConfigurationPropertiesBindingPostProcessor getConfigurationPropertiesBindingPostProcessor() {

        return new ConfigurationPropertiesBindingPostProcessor();
    }

    @Bean("org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata")
    public ConfigurationBeanFactoryMetadata getConfigurationBeanFactoryMetadata() {

        return new ConfigurationBeanFactoryMetadata();
    }
}
