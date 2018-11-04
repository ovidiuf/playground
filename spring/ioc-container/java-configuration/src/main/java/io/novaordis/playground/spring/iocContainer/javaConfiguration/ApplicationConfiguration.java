package io.novaordis.playground.spring.iocContainer.javaConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public MainComponent getMainComponent() {

        return new MainComponent();
    }

    @Bean
    public DependencyImpl getDependency() {

        return new DependencyImpl("something");
    }

}
