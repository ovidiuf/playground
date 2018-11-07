package some.experimental.dependency;

/**
 * This is a dependency class that is invoked into by the SpringBoot framework directly, without an assumption of
 * Spring component availability: the Dependency class instance is created with new or by reflection. The situation
 * is similar to a Spring Boot application instantiating a JPA Converter class specified in a third party library.
 * The Dependency instance gets the Spring-managed singleton instance via a static method call.
 */
public class Dependency {

    private DependencySpringComponent springComponent;

    public Dependency() {

        //
        // The DependencySpringComponent singleton is initialized by Spring by now and installed into the application
        // context; we get it by calling into DependencySpringComponent static method:
        //
        springComponent = DependencySpringComponent.getSpringBeanInstance();
    }

    public void run() {

        System.out.println("#");
        System.out.println("# " + this + " is running with Spring component " + springComponent);
        System.out.println("#");

        springComponent.run();
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
