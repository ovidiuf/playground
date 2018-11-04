package io.novaordis.playground.spring.iocContainer.explicitApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class MainComponent {

    private DependencyA dependency;

    private ApplicationContext applicationContext;

    public MainComponent() {

        applicationContext = new ClassPathXmlApplicationContext("blue-red-beans.xml");
    }

    public void run() {

        System.out.println("main component was invoked ....");

        dependency = (DependencyA)applicationContext.getBean("blue");

        dependency.run();
    }
}
