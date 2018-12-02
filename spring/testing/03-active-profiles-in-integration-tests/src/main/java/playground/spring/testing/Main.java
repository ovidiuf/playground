package playground.spring.testing;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static AnnotationConfigApplicationContext APPLICATION_CONTEXT;

    static {

        APPLICATION_CONTEXT = new AnnotationConfigApplicationContext();
        APPLICATION_CONTEXT.addApplicationListener(new ConfigFileApplicationListener());
        APPLICATION_CONTEXT.scan("playground.spring.testing");
        APPLICATION_CONTEXT.refresh();
    }

    public static void main(String[] args) throws Exception {

        AComponent c = APPLICATION_CONTEXT.getBean(AComponent.class);

        // TODO bean by name

        System.out.println(c.getPropertyConfiguration().getName());
    }
}
