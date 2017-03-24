# Session EJB and Servlet Collocated in Same WAR, @EJB Injection

https://kb.novaordis.com/index.php/Session_EJB_and_Servlet_Collocated_in_Same_WAR,_@EJB_Injection#Overview
 
# To Build

````
mvn clean install
````

# To Deploy

In this order:

````
cp ./target/wrapper-servlet.war $JBOSS_HOME/profiles/$JBOSS_PROFILE/deployments

````

# To Run

````
./bin/invoke
````
 



