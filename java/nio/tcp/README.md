# Java NIO and TCP Connections

# Overview

An application that let the user establish a TCP connection between a client and server.
The connection endpoints are handled asynchronously with Java NIO. Both the client and 
the server enter in a command-line loop that allows sending text bidirectionally.

# NOKB

https://kb.novaordis.com/index.php/Java_NIO_and_TCP_Connections#Overview

# Build

````
./bin/build-and-deploy
````

It will deploy (then update) ~/tmp/java-nio-tcp

# Run

Start the server:

````
cd ~/tmp
./java-nio-tcp/bin/server
````

Start then client:

````
cd ~/tmp
./java-nio-tcp/bin/client
````

After establishing the connection, both client and server can send text typed into their consoles to each other.