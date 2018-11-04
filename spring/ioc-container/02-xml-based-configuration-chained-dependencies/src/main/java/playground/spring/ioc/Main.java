package playground.spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    static ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("blue-red-beans.xml");

    public static void main(String[] args) {

        MainComponent mc = new MainComponent();

        mc.run();
    }
}
