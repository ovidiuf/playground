#Simple HornetQ JMS Client

This is a simple, maven-built, command-line configured command-line client that interacts with a
WildFly-embedded HornetQ server.

To build:

    mvn clean install

Then update ./bin/common so it builds the classpath correctly on the machine you're executing the
client.

Prepare the target WildFly instances you intend to connect to by:
* Disabling authentication on the remoting subsystem (so JNDI calls can be performed without authentication)
* Adding jmsuser/jmsuser123 with $JBOSS_HOME/bin/add-user.sh to the ApplicationRealm, and assign it to the
"guest" role.

##Sending Messages

Then, to send messages (more help in-line by simply running ./send):

    ./bin/send \
     --jndi 127.0.0.1:4447 \
     --destination /queue/novaordis \
     --connection-factory /jms/RemoteConnectionFactory \
     [--username jmsuser --password jmsuser123] \
     [--connections 10] \
     [--threads 10] \
     [--sleep-between-sends-ms 1000] \
     [--messages 100]

The client sends the specified number of messages (or just one message if --message is omitted).

If --sleep-between-sends-ms is used, the client sleeps the specified number of milliseconds after
sending a message - on each thread. If the option is not used, the client sends the specified
number of messages as fast as it can and exits.

If no --username (and corresponding --password) options are specified, the JMS Connections will be
created anonymously. The server may or may not allow anonymous connections, depending on its
security settings.

Just only one connection is created by default, unless --connections is used.

Just only one thread is used to send messages, by default, unless --threads is used.

##Receiving Messages

To receive messages (more help in-line by simply running ./receive):

    ./bin/receive \
     --jndi 127.0.0.1:4447 \
     --destination /queue/novaordis \
     --connection-factory /jms/RemoteConnectionFactory \
     [--username jmsuser --password jmsuser123] \
     [stats]

The client listens for incoming messages and dumps them at stdout as they arrive or displays
statistics if the "--stats" option was used.

If no --username (and corresponding --password) options are specified, the JMS Connections will be
created anonymously. The server may or may not allow anonymous connections, depending on its
security settings.

#Also See

https://github.com/NovaOrdis/playground/tree/master/wildfly/hornetq/failover-testing-framework