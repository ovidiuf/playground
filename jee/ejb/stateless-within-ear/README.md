# Stateless EJB Example deployed within an EAR

A simple stateless EJB that can be invoked into from a wrapper servlet. Both the EJB and the servlet 
are deployed together as part of the same EAR.

The EJB bean reference is looked up by the servlet in JNDI.

 
# To Build

````
mvn clean install
````

# To Deploy

In this order:

````
cp ./ear/target/stateless-ejb-and-servlet.ear $JBOSS_HOME/profiles/$JBOSS_PROFILE/deployments

````

# To Run

````
./bin/invoke
````
 

# NOKB

https://kb.novaordis.com/index.php/Stateless_EJB#Example


