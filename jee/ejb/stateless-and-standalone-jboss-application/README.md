# Session EJB invoked from a Standalone Application

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
 

