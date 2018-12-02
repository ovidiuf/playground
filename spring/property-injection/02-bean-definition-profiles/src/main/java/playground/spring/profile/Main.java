package playground.spring.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {

        declarativeProfileActivation();

        //programmaticProfileActivation();
    }

    private static void declarativeProfileActivation() {

        // -Dspring.profiles.active, SPRING_PROFILES_ACTIVE, etc. can be used

        final AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("playground.spring.profile");

        ColorAware component = applicationContext.getBean(ColorAware.class);

        System.out.println(component);
    }

    private static void programmaticProfileActivation() {

        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("blue");
        applicationContext.scan("playground.spring.profile");
        applicationContext.refresh();

        ColorAware component = applicationContext.getBean(ColorAware.class);

        System.out.println(component);
    }

}
