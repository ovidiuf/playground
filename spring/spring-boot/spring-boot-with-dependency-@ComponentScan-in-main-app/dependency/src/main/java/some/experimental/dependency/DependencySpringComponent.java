package some.experimental.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This is a Spring bean that is identified by component scanning performed by the main application and is installed
 * into the Spring application context. As part of its initialization cycle, it retains a reference to the application
 * context and it is exposing itself via a static method that has access to the application context.
 */
@SuppressWarnings("WeakerAccess")
@Component
public class DependencySpringComponent {

    private static ApplicationContext APPLICATION_CONTEXT;

    @Autowired
    public DependencySpringComponent(ApplicationContext applicationContext) {

        System.out.println("#");
        System.out.println("# " + this + " is being constructed as part of Spring Singleton initialization, got application context " + applicationContext);
        System.out.println("#");

        APPLICATION_CONTEXT = applicationContext;
    }

    public static DependencySpringComponent getSpringBeanInstance() {

        if (APPLICATION_CONTEXT == null) {

            throw new IllegalStateException("access to Spring ApplicationContext has not been configured");
        }

        return APPLICATION_CONTEXT.getBean(DependencySpringComponent.class);
    }

    public void run() {

        System.out.println("#");
        System.out.println("# " + this + " is running");
        System.out.println("#");
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
