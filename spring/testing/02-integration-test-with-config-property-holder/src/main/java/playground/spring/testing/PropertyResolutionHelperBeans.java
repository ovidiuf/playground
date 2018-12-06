package playground.spring.testing;

import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBeanFactoryMetadataProvider {

    @Bean
    public ConfigurationPropertiesBindingPostProcessor getConfigurationPropertiesBindingPostProcessor() {

        return new ConfigurationPropertiesBindingPostProcessor();
    }

    @Bean("org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata")
    public static ConfigurationBeanFactoryMetadata getConfigurationBeanFactoryMetadata() {

        return new ConfigurationBeanFactoryMetadata();
    }

}
