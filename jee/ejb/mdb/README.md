# Playground MDB

A simple example of an EJB3 MDB. Deploys as a JAR.

# NOKB

https://kb.novaordis.com/index.php/MDB#API

# Server Configuration Requirements

A "playground" queue must be deployed on the server. This is how the queue is declared for a HornetQ-based EAP:

````
<jms-destinations>
    <jms-queue name="playground">
        <entry name="java:/jms/queue/playground"/>
    </jms-queue>
</jms-destinations>
````

