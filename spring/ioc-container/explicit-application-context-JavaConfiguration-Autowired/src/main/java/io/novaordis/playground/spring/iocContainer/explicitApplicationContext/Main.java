package io.novaordis.playground.spring.iocContainer.explicitApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext =  new ClassPathXmlApplicationContext("blue-beans.xml");

        MainComponent mc = applicationContext.getBean(MainComponent.class);

        mc.run();
    }
}
