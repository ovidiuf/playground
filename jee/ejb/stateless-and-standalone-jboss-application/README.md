# Session EJB invoked from a Standalone Application

__TODO__: the example is not fully implemented and may be confusing, though it can be used as a base for the second iteration. Re-iterate next time I need it.Also see https://kb.novaordis.com/index.php/Standalone_Application_Invocation_into_a_Remote_Session_EJB#GitHub_Example.

https://kb.novaordis.com/index.php/Standalone_Application_Invocation_into_a_Remote_Session_EJB#GitHub_Example
 
# To Build

````
mvn clean install
````

# To Deploy

In this order:

````
cp ./stateless-ejb/target/stateless-ejb-example.jar $JBOSS_HOME/profiles/$JBOSS_PROFILE/deployments

````

# To Run

````
./bin/invoke
````
 

