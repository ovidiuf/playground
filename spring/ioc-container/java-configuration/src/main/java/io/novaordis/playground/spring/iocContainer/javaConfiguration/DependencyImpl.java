package io.novaordis.playground.spring.iocContainer.javaConfiguration;

public class DependencyImpl implements Dependency {

    private String arg;

    public DependencyImpl(String arg) {

        this.arg = arg;
    }

    @Override
    public void run() {

        System.out.println("DependencyImpl[" + arg + "] running ...");
    }
}
