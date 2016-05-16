#HornetQ Failover Testing Framework

A simple JMS client that sends messages in a loop, while recording their IDs and failures
(if they occur), so the set of messages that was effectively sent can be compared with the set of 
received messages.

Builds in top of the "simplest-client" so all the options supported by that client are available here.

    ./bin/send \
     --jndi 127.0.0.1:4447 \
     --destination /queue/novaordis \
     --connection-factory /jms/RemoteConnectionFactory \
     [--username jmsuser --password jmsuser123] \
     [--connections 10] \
     [--threads 10] \
     [--sleep-between-sends-ms 1000] \
     [--messages 100] \
     --output /fully/qualified/name/of/the/file/to/write/statistics/into.csv


https://github.com/NovaOrdis/playground/tree/master/wildfly/hornetq/simplest-client

