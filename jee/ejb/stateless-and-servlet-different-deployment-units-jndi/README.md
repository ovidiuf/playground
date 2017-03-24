# Stateless EJB Example via JNDI

A simple stateless that can be invoked into from a wrapper servlet. The EJB and the servlet are deployed 
as separated JBoss modules.

The EJB bean reference is looked up by the servlet in JNDI.

The EJB interface type is made available to the servlet module via module dependency declared in
jboss-deployment-structure.xml.

 
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


