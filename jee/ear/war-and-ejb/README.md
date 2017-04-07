# Stateless EJB and Invoking Servlet Deployed within an EAR, JNDI Lookup

https://kb.novaordis.com/index.php/Session_EJB_and_Servlet_as_Different_EAR_Modules,_JNDI_Lookup
 
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
 


