#HornetQ Failover Testing Framework

A simple JMS client that sends messages in a loop, while recording their IDs and failures
(if they occur), so the set of messages that was effectively sent can be compared with the set of 
received messages.

Builds in top of the "simplest-client" so all the options supported by that client are available here.

https://github.com/NovaOrdis/playground/tree/master/wildfly/hornetq/simplest-client

