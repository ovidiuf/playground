# Sending and Receiving Messages from Remote Destinations, Destinations are Injected with @Remote

# Overview

This is an example of how a JEE component (servlet in this case) can send and receive
messages to/from JMS destinations deployed on a remote JMS server, that was exposed
locally via a resource adapter.

The remote destinations are injected with @Remote

The EAP component to be deployed into must be configured in advance as described here: 
https://kb.novaordis.com/index.php/Configuring_a_Remote_HornetQ_JMS_Server_as_a_Resource_Adapter

Also see:

* https://kb.novaordis.com/index.php/Configuring_a_Remote_HornetQ_JMS_Server_as_a_Resource_Adapter#Sending_Messages_to_Remote_Destinations
* https://kb.novaordis.com/index.php/WildFly_Naming_Subsystem_Configuration#Importing_an_External_JNDI_Context
* https://kb.novaordis.com/index.php/WildFly_HornetQ-Based_Messaging_Subsystem_Concepts#Resource_Adapter

# Build

```
mvn clean install
```

# Execute

http://localhost:8080/wrapper-servlet/send/

http://localhost:8080/wrapper-servlet/receive/


