# Session EJB and Servlet in Different Deployment Units on Same JBoss Instance, JNDI Lookup

https://kb.novaordis.com/index.php/Session_EJB_and_Servlet_in_Different_Deployment_Units_on_Same_JBoss_Instance,_JNDI_Lookup#GitHub_Example
 
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
 

