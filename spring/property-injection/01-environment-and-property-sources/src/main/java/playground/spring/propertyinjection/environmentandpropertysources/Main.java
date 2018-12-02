package playground.spring.propertyinjection.environmentandpropertysources;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static ApplicationContext APPLICATION_CONTEXT =
            new AnnotationConfigApplicationContext("playground.spring.propertyinjection.environmentandpropertysources");

    public static void main(String[] args) throws Exception {

        APPLICATION_CONTEXT.getEnvironment();

        ComponentA a = APPLICATION_CONTEXT.getBean(ComponentA.class);

        a.inspectEnvironment();

        a.checkProperty(args[0]);
    }
}
