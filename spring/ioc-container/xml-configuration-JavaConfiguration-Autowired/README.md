#Spring Inversion of Control Container

In this example, the ApplicationContext is initialized by an application
component, and there is just one bean in the context.

The configuration metadata is provided as XML.

The dependency is not pulled with getBean() but injected by framework as
result of the @Autowire annotation.