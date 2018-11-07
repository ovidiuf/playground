package playground;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("WeakerAccess")
public class Main {

    static AnnotationConfigApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext("playground");

    public static void main(String[] args) {

        Blue blue = APPLICATION_CONTEXT.getBean(Blue.class);

        String s = blue.run();

        System.out.println(s);
    }
}
