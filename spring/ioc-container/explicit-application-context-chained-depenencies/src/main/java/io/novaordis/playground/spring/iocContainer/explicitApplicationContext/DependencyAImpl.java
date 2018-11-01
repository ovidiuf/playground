package io.novaordis.playground.spring.iocContainer.explicitApplicationContext;

public class DependencyAImpl implements DependencyA {

    private String arg;

    private DependencyB dependency;

    public DependencyAImpl(String arg, DependencyB dependency) {

        this.arg = arg;

        this.dependency = dependency;
    }

    @Override
    public void run() {

        System.out.println("DependencyAImpl[" + arg + "] was invoked ...");

        dependency.run();
    }
}
