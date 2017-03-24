# Session EJB and Servlet on Different JBoss Instances

https://kb.novaordis.com/index.php/Session_EJB_and_Servlet_on_Different_JBoss_Instances
 
# To Build

````
mvn clean install
````

# To Deploy

In this order:

````
cp ./stateless-ejb/target/stateless-ejb-example.jar $JBOSS_HOME/profiles/$JBOSS_PROFILE/deployments
cp ./servlet/target/wrapper-servlet.war $JBOSS_HOME/profiles/$JBOSS_PROFILE/deployments

````

# To Run

````
./bin/invoke
````
 

