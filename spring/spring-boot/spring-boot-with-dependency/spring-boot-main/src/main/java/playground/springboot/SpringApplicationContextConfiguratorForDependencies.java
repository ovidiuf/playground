package playground.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import playground.springboot.dependency.SpringApplicationContextAccess;

/**
 * This component should be used early in the life cycle of the SpringBoot runtime to configure the dependency's
 * SpringApplicationContextAccess.
 *
 * @see playground.springboot.dependency.SpringApplicationContextAccess
 */
@Component
@Slf4j
public class SpringApplicationContextConfiguratorForDependencies {

    @Autowired
    public SpringApplicationContextConfiguratorForDependencies(ApplicationContext applicationContext) {

        log.info("{} is configuring the dependency with ApplicationContext access ...", this);

        SpringApplicationContextAccess.APPLICATION_CONTEXT = applicationContext;
    }
}
