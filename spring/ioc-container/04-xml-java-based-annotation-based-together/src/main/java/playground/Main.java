package playground;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("WeakerAccess")
public class Main {

    static AnnotationConfigApplicationContext APPLICATION_CONTEXT;

    static {

        APPLICATION_CONTEXT = new AnnotationConfigApplicationContext("playground");
        APPLICATION_CONTEXT.register(ApplicationConfiguration.class);
        APPLICATION_CONTEXT.register(SupplementalXmlConfiguration.class);
    }

    public static void main(String[] args) {

        Blue blue = APPLICATION_CONTEXT.getBean(Blue.class);

        String s =  blue.run();

        System.out.println(s);
    }
}
