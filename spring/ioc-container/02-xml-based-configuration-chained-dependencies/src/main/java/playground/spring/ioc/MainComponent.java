package playground.spring.ioc;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class MainComponent {

    private DependencyA dependency;

    public void run() {

        System.out.println("main component was invoked ....");

        dependency = (DependencyA)Main.APPLICATION_CONTEXT.getBean("blue");

        dependency.run();
    }
}
