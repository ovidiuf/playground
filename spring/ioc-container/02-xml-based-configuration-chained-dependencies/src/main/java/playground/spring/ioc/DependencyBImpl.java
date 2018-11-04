package playground.spring.ioc;

public class DependencyBImpl implements DependencyB {

    private String arg;

    public DependencyBImpl(String arg) {

        this.arg = arg;
    }

    @Override
    public void run() {

        System.out.println("DependencyBImpl[" + arg + "] was invoked ...");
    }
}
