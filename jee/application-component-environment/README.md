# Application Component Environment

To build and deploy:

````
mvn clean install
cp ./ear/target/war-and-ejb.ear <deployment-dir>

````    

If correctly deployed, the application will be available under the root context "/invoker".

To exercise:

http://localhost:8080/invoke/

# NOKB

https://kb.novaordis.com/index.php/JEE_Core_Concepts_-_Resources,_Naming_and_Injection#Application_Component_Environment
