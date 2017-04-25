# MDB Failure Handling

A project containing infrastructure to use when experimenting with MDB failure handling.

See https://kb.novaordis.com/index.php/MDB_Failure_Handling

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