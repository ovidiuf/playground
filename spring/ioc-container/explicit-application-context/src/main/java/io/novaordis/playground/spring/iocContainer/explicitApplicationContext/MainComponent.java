package io.novaordis.playground.spring.iocContainer.explicitApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("WeakerAccess")
public class MainComponent {

    private Dependency dependency;

    private ApplicationContext applicationContext;

    public MainComponent() {

        applicationContext = new ClassPathXmlApplicationContext("blue-beans.xml");
    }

    public void run() {

        System.out.println("main component running ....");

        dependency = (Dependency)applicationContext.getBean("blue");

        dependency.run();
    }
}
