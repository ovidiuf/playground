#Spring Inversion of Control Container

In this example, the ApplicationContext is initialized by an application
component, and there are two chained dependencies: the main component interacts
with a DependencyA instance and DependencyA instance needs a DependencyB instance.