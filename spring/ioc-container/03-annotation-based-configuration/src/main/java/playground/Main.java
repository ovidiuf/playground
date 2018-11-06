package playground;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SuppressWarnings("WeakerAccess")
@ComponentScan("playground")
public class Main {

    static AnnotationConfigApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext();

    public static void main(String[] args) {

        APPLICATION_CONTEXT.refresh();

        Blue blue = APPLICATION_CONTEXT.getBean(Blue.class);

        blue.run();

        Green green = (Green) APPLICATION_CONTEXT.getBean("green");

        green.run();

        Green unmanagedGreen = new Green("something");

        unmanagedGreen.run();
    }
}
