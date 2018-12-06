package playground.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.scan("playground.spring");

        new ConfigFileApplicationListener().
                postProcessEnvironment(applicationContext.getEnvironment(), new SpringApplication());

        applicationContext.refresh();

        System.out.println("'playground.spring.color' in environment: " + applicationContext.getEnvironment().getProperty("playground.spring.color"));

        SimpleConfigurationPropertyHolder h = applicationContext.getBean(SimpleConfigurationPropertyHolder.class);

        System.out.println("property holder color: " + h.getColor());
    }
}
