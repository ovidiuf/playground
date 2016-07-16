#Infinispan HotRod Client

##Overview

A simple interactive client that connects to an Infinispan/JDG cluster over the HotRod protocol. The "connect" command
creates a RemoteCacheManager and retrieves the default cache. Once the connection is established, the client allows to 
interactively read and write the cache.

##Build

Access to a JDG server Maven repository is required. Update the details of the <repository> declaration to match your
local environment. 
 

    ...
    
    <repositories>
        <repository>
            <id>jboss-datagrid-6.6.0-maven-repository</id>
            <url>file:///Users/ovidiu/runtime/jboss-datagrid-6.6.0-maven-repository</url>
        </repository>
    </repositories>
    
    ...
    
Then you can build:
    
    mvn clean package

The ./bin/hotrod-client wrapper must also be updated to point to the JDG server deployment we're going to connect
to:

    jboss_home=~/runtime/jboss-datagrid-6.6.0-server
    infinispan_version=6.4.0.Final-redhat-4

##Usage Example

In order to use the example, you will need to build it first, see above.

    cd .../hotrod-client
    ./bin/hotrod-client
    > connect localhost:11222:some-cache
    > put test-key test-value
    > get test-key
    test-value
    > exit
   
In the example above, we connected the client to a HotRod server, while specifying a cache name. All connection
elements (host, port, cache name) are optional. For a JDG server, the host:port pair is associated with a specific 
hotrod connector, and the hotrod connector is associated with a specific cache container; it is that cache container
the client gets access to. This association is described in the JDG server's configuration file. If no cache name is
specified, the default cache of the cache container is used. If a name of a cache that does not exist is used, the 
client will display an error message.

   
##See Also

https://kb.novaordis.com/index.php/Infinispan_Tools#Tools
 
