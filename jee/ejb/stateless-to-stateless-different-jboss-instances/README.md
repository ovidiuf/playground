# EJB to EJB call over Native Remoting, different JBoss Instances
 
To run the example, the "caller" and the "callee" JBoss instances must be configured as described here:

https://kb.novaordis.com/index.php/Session_EJB_and_Servlet_on_Different_JBoss_Instances

Sample JBoss configuration files have been provided in ./jboss-configuration/caller and ./jboss-configuration/callee

# Workflow

````
mvn clean install
./bin/deploy-callee
./bin/deploy-caller
./bin/invoke
````


