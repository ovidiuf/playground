package playground;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("WeakerAccess")
public class Main {

    static AnnotationConfigApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext("playground");

    public static void main(String[] args) {

        Blue blue = APPLICATION_CONTEXT.getBean(Blue.class);

        blue.run();

//        Green green = APPLICATION_CONTEXT.getBean(Green.class);
//
//        green.run();
//
//        Green unmanagedGreen = new Green("something");
//
//        unmanagedGreen.run();
    }
}
