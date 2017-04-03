# Sending and Receiving Messages from Remote Destinations

# Overview

This is an example of how a JEE component (servlet in this case) can send and receive
messages to/from JMS destinations deployed on a remote JMS server, that was exposed
locally via a resource adapter.

For more details see:

* https://kb.novaordis.com/index.php/Configuring_a_Remote_HornetQ_JMS_Server_as_a_Resource_Adapter
* https://kb.novaordis.com/index.php/Configuring_a_Remote_HornetQ_JMS_Server_as_a_Resource_Adapter#Sending_Messages_to_Remote_Destinations

In order to execute the example, the remote JMS server has to exposed as described in
the above articles.

#Build

```
mvn clean install
```



