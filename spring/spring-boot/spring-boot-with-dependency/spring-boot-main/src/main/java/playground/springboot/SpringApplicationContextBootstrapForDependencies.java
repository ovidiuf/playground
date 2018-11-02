package playground.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import playground.springboot.dependency.Dependency;

@Component
public class SpringApplicationContextBootstrapForDependencies {

    @Autowired
    private ApplicationContext applicationContext;

    public void prepareContextForDependencies() {

        Dependency.configureSpringApplicationContext(applicationContext);
    }

    @Override
    public String toString() {

        return "SpringApplicationContextBootstrapForDependencies[application context: " + applicationContext + "]";
    }
}
