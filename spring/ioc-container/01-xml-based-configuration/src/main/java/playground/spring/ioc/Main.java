package playground.spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    static ApplicationContext APPLICATION_CONTEXT;

    public static void main(String[] args) {

        APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("blue-beans.xml");

        MainComponent mc = new MainComponent();

        mc.run();
    }
}
