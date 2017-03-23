# JEE EAR with an embedded lib and WAR

An example of how to build a simple JEE EAR with an embedded lib and WAR.
The functionality is not relevant, but the Maven configuration that builds
it is.

To build and deploy:

    mvn clean install
    cp ./ear/target/ear-example.ear <deployment-dir>
    
If correctly deployed, the application will be available under the root context "/example".

Tested with:

* JBoss EAP 5.2     

Knowledge Base:

* https://kb.novaordis.com/index.php/Maven_ear_Plugin

