package playground.spring.testing;

import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBeanFactoryMetadataProvider {

    @Bean("org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata")
    public static ConfigurationBeanFactoryMetadata getConfigurationBeanFactoryMetadata() {

        return new ConfigurationBeanFactoryMetadata();
    }
}
