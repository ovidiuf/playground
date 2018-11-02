package playground.springboot.dependency;

import org.springframework.context.ApplicationContext;

/**
 * This class exists with the sole purpose of giving other classes from this project access to the runtime's
 * Spring ApplicationContext. The main runtime should configured this class and "seed" it with an ApplicationContext
 * instance early in its life cycle. Conventionally it does that with a
 * SpringApplicationContextConfiguratorForDependencies bean.
 */
public class SpringApplicationContextAccess {

    public static ApplicationContext APPLICATION_CONTEXT;

}
