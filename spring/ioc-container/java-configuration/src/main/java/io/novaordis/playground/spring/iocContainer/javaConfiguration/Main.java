package io.novaordis.playground.spring.iocContainer.javaConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext c = new AnnotationConfigApplicationContext();

        c.register(ApplicationConfiguration.class);

        c.refresh();

        MainComponent mc = c.getBean(MainComponent.class);

        mc.run();
    }
}
