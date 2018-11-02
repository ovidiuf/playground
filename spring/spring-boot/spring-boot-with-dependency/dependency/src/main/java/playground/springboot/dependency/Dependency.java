package playground.springboot.dependency;

public class Dependency {

    private DependencySpringComponentA springComponent;

    public Dependency() {

        configureAccessToSpringComponent();
    }

    public void run() {

        System.out.println(this + " is running with Spring component " + springComponent);

        springComponent.run();
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    /**
     * Use this method in the constructor to explicitly pull the bean from the context.
     *
     * @throws IllegalStateException if we encounter bad state because the initialization was not performed.
     */
    private void configureAccessToSpringComponent() throws IllegalStateException {

        if (SpringApplicationContextAccess.APPLICATION_CONTEXT == null) {

            throw new IllegalStateException("access to Spring ApplicationContext has not been configured");
        }

        springComponent = SpringApplicationContextAccess.APPLICATION_CONTEXT.getBean(DependencySpringComponentA.class);

        if (springComponent == null) {

            throw new IllegalStateException(
                    "a component of type " + DependencySpringComponentA.class + " not found in application context");
        }
    }
}
