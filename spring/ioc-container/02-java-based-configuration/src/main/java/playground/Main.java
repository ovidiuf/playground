package playground;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("WeakerAccess")
public class Main {

    static ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

    public static void main(String[] args) {

        Blue blue = (Blue)APPLICATION_CONTEXT.getBean("blue");

        blue.run();

        Green green = (Green) APPLICATION_CONTEXT.getBean("green");

        green.run();

        Green unmanagedGreen = new Green("something");

        unmanagedGreen.run();
    }
}
