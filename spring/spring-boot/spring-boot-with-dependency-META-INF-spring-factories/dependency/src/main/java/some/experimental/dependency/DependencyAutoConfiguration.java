package some.experimental.dependency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyAutoConfiguration {

    @Bean
    public DependencySpringComponent dependencySpringComponent(ApplicationContext applicationContext) {

        return new DependencySpringComponent(applicationContext);
    }
}
