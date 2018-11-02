package playground.springboot.dependency;

import org.springframework.context.ApplicationContext;

public class Dependency {

    private static ApplicationContext applicationContext;

    public static void configureSpringApplicationContext(ApplicationContext ac) {

        applicationContext = ac;

        System.out.println("Dependency.class set application context to " + ac);
    }

    private DependencySpringComponentA a;

    public Dependency() {

        a = applicationContext.getBean(DependencySpringComponentA.class);
    }

    public void run() {

        System.out.println(this + " is running with Spring component " + a);;
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
