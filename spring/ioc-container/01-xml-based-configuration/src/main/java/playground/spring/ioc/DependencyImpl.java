package playground.spring.ioc;

import playground.spring.ioc.Dependency;

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
