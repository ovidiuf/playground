package playground.dependency;

import org.springframework.context.ApplicationContext;

/**
 * This class exists with the sole purpose of giving other classes from this project access to the runtime's
 * Spring ApplicationContext. The main runtime should configured this class and "seed" it with an ApplicationContext
 * instance early in its life cycle. Conventionally it does that with a
 * SpringApplicationContextConfiguratorForDependencies bean.
 */
@SuppressWarnings("WeakerAccess")
public class SpringApplicationContextAccess {

  private static ApplicationContext APPLICATION_CONTEXT;

  public static void installApplicationContext(ApplicationContext ac) {

    APPLICATION_CONTEXT = ac;

  }

  /**
   * Use this method to explicitly pull the bean from the context.
   *
   * @return may return null if no such bean exists in the application context.
   *
   * @throws IllegalStateException if we encounter bad state because the initialization was not performed.
   */
  public static <T> T getBean(Class<T> type) throws IllegalStateException {

    if (APPLICATION_CONTEXT == null) {

      throw new IllegalStateException("access to Spring ApplicationContext has not been configured");
    }

    return APPLICATION_CONTEXT.getBean(type);
  }
}
