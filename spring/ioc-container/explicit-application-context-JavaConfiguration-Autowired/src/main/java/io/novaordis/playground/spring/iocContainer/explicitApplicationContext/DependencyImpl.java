package io.novaordis.playground.spring.iocContainer.explicitApplicationContext;

import org.springframework.stereotype.Component;

@Component
public class DependencyImpl implements Dependency {

    private String arg;

    public DependencyImpl(String arg) {

        this.arg = arg;
    }

    public DependencyImpl() {

        this("default something");
    }

    @Override
    public void run() {

        System.out.println("DependencyImpl[" + arg + "] running ...");
    }
}
