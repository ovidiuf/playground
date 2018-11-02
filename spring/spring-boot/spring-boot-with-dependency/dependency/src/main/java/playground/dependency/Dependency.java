package playground.dependency;

import org.springframework.beans.factory.annotation.Autowired;

public class Dependency {

    @Autowired
    private DependencySpringComponentA springComponent;

    public Dependency() {

        springComponent = SpringApplicationContextAccess.getBean(DependencySpringComponentA.class);
    }

    public void run() {

        System.out.println(this + " is running with Spring component " + springComponent);

        springComponent.run();
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

}
