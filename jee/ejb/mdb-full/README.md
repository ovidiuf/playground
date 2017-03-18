# Playground MDB Full Example

An MDB example that showcases many of the configuration and API options described here https://kb.novaordis.com/index.php/MDB

# NOKB

https://kb.novaordis.com/index.php/MDB

# Server Configuration Requirements

A "playground" queue must be deployed on the server. This is how the queue is declared for a HornetQ-based EAP:

````
<jms-destinations>
    <jms-queue name="playground">
        <entry name="java:/jms/queue/playground"/>
        <entry name="java:jboss/exported/jms/queue/playground"/>
    </jms-queue>
</jms-destinations>
````


Upon deployment, the MDB will react to a message sent with:

````
send --jndi 127.0.0.1:4447 --destination /jms/queue/playground --connection-factory /jms/RemoteConnectionFactory --messages 1
````