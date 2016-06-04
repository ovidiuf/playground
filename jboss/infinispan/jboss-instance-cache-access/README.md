#JBoss Internal Cache Access Tool

#Overview

A web application (servlet) that gives access to the internal EAP/JDG caches, from within an EAP/JDG instance. 

#NOKB

https://kb.novaordis.com/index.php/Infinispan_Tools

#Build

    mvn clean pacakge

#Deploy

    cp ./target/jbca.war $JDG_HOME/standalone/deployments
    
    

