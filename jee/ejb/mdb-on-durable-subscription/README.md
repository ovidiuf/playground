# Playground MDB on Durable Subscription

An MDB example that shows behavior in presence of a durable topic subscription.

# NOKB

https://kb.novaordis.com/index.php/MDB

# Server Configuration Requirements

A "playground" topic must be deployed on the server. 

This is how the topic is declared for a HornetQ-based EAP:

````
<jms-destinations>
    <jms-topic name="playground">
        <entry name="java:/jms/topic/playground-topic"/>
        <entry name="java:jboss/exported/jms/topic/playground-topic"/>
    </jms-topic>
</jms-destinations>
````

This is how the topic is declared on an ActiveMQ-based EAP:

````
<jms-topic name="playground-topic" 
           entries="topic/playground-topic jms/topic/playground-topic java:jboss/exported/jms/topic/playground-topic"/>
````

Upon deployment, the MDB will react to a message sent with:

````
send --jndi 127.0.0.1:[4447|8080] --destination /jms/queue/playground --connection-factory /jms/RemoteConnectionFactory --messages 1
````

(for the client, see playground/jboss/hornetq|activemq/simplest-client).