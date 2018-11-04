package playground.spring.ioc;

@SuppressWarnings("WeakerAccess")
public class MainComponent {

    private Dependency dependency;

    public void run() {

        System.out.println("main component running ....");

        dependency = (Dependency) Main.APPLICATION_CONTEXT.getBean("blue");

        dependency.run();
    }
}
