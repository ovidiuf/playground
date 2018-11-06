package playground;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("WeakerAccess")
public class Main {

    static ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("beans.xml");

    public static void main(String[] args) {

        Blue blue = (Blue)APPLICATION_CONTEXT.getBean("blue");

        blue.run();

    }
}
