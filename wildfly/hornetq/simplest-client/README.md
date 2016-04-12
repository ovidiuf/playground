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

Then, to send messages (more help in-line by simply running ./send):

    ./bin/send \
      --jndi 127.0.0.1:4447 \
      --destination /topic/novaordis \
      --connection-factory /jms/RemoteConnectionFactory \
      --messages 1000 \
      --threads 10

To receive messages (more help in-line by simply running ./receive):

    ./bin/receive \
    --jndi 127.0.0.1:4447 \
    --destination /queue/toyota \
    --connection-factory /jms/RemoteConnectionFactory
    

   


