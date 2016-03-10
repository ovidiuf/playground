#Simple HornetQ JMS Client

This is a simple, maven-built, command-line configured command-line client that interacts with a
WildFly-embedded HornetQ server.

To build:

    mvn clean install

Then update ./bin/common so it builds the classpath correctly on the machine you're executing the
client.

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
    

   


