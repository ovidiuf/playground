package io.novaordis.playground.spring.iocContainer.javaConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("WeakerAccess")
public class MainComponent {

    @Autowired
    private DependencyImpl dependency;

    public MainComponent() {
    }

    public void run() {

        System.out.println("main component running ....");

        dependency.run();
    }
}
