package io.novaordis.playground.spring.iocContainer.explicitApplicationContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
public class MainComponent {

    @Autowired
    private Dependency dependency;

    public void run() {

        System.out.println("main component running ....");

        //
        // the dependency is autowired, no need for getBean()
        //
        // dependency = (Dependency)applicationContext.getBean("blue");

        dependency.run();
    }
}
