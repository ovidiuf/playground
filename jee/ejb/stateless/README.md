# Stateless EJB Example

A simple stateless that can be invoked into from a wrapper servlet.
 
# To Build

````
mvn clean install
````

# To Deploy

````
cp ./servlet/target/wrapper-servlet.war $JBOSS_HOME/profiles/$JBOSS_PROFILE/deployments
cp ./stateless-ejb/target/stateless-ejb-example.jar $JBOSS_HOME/profiles/$JBOSS_PROFILE/deployments
````

# To Run

````
./bin/invoke
````
 

# NOKB

https://kb.novaordis.com/index.php/Stateless_EJB#Example


