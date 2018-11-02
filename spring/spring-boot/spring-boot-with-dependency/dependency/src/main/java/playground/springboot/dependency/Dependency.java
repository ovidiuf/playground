package playground.springboot.dependency;

public class Dependency {

  private DependencySpringComponentA springComponent;

  public Dependency() {

    springComponent = SpringApplicationContextAccess.getBean(DependencySpringComponentA.class);
  }

  public void run() {

    System.out.println(this + " is running with Spring component " + springComponent);

    springComponent.run();
  }

  @Override
  public String toString() {

    return getClass().getSimpleName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
  }

}
