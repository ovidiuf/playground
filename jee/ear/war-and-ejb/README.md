# JEE EAR with embedded web application WAR and EJB

An example of how to build a simple JEE EAR with an embedded EJB JAR and a web application WAR. 

To build and deploy:

````
mvn clean install
cp ./ear/target/war-and-ejb.ear <deployment-dir>

````    

If correctly deployed, the application will be available under the root context "/invoker".

To exercise:

http://localhost:8080/invoke/

# NOKB

https://kb.novaordis.com/index.php/EAR#Examples
