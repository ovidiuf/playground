# Stateless EJB Example being Invoked Into via a Deployed EJB Client Context

This example contains a simple stateless EJB that is deployed within a JBoss instance
and a completely separate servlet WAR that is deployed into a separate, remote JBoss instance.
The servlet invokes the EJB via an EJB Client Context, which is declared using a
jboss-ejb-client.xml deployment descriptor. For more details on jboss-ejb-client.xml, 
see https://kb.novaordis.com/index.php/Jboss-ejb-client.xml.

Because the servlet needs access to the business interface type, that is developed
in a separate Maven module, which will produce its own JAR, that will be included in both
servlet and EJB deployment.

 
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
 

# NOKB

https://kb.novaordis.com/index.php/Stateless_EJB#Example


