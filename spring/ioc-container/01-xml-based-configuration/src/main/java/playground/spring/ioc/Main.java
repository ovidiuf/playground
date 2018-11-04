package playground.spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("WeakerAccess")
public class Main {

    static ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("beans.xml");

    public static void main(String[] args) {

        Blue blue = APPLICATION_CONTEXT.getBean(Blue.class);

        blue.run();
    }
}
