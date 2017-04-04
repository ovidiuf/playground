# Sending and Receiving Messages from Remote Destinations, Destinations are Injected with @Remote

# Overview

This is an example of how a JEE component (servlet in this case) can send and receive
messages to/from JMS destinations deployed on a remote JMS server, that was exposed
locally via a resource adapter.

The remote destinations are injected with @Remote

For more details see:

* https://kb.novaordis.com/index.php/Configuring_a_Remote_HornetQ_JMS_Server_as_a_Resource_Adapter
* https://kb.novaordis.com/index.php/Configuring_a_Remote_HornetQ_JMS_Server_as_a_Resource_Adapter#Sending_Messages_to_Remote_Destinations
* https://kb.novaordis.com/index.php/WildFly_Naming_Subsystem_Configuration#Importing_an_External_JNDI_Context


In order to execute the example, the remote JMS server has to exposed as described in
the above articles.

# Build

```
mvn clean install
```

# Execute

http://localhost:8080/wrapper-servlet/send/

http://localhost:8080/wrapper-servlet/receive/


