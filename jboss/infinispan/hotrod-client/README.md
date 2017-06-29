# Infinispan HotRod Client

## Overview

A simple interactive client that connects to an Infinispan/JDG cluster over the HotRod protocol. The "connect" command
creates a RemoteCacheManager and retrieves the default cache. Once the connection is established, the client allows to 
interactively read and write the cache.

## Build

The client can be built against multiple versions of Infinispan and JDG. By default, if no profile is specified, 
it is built against Infinispan 8.2.4.Final. 

If you want to build the client against a specific JDG version, you will need to have access to the corresponding
Maven repository, which will have to be specified in the appropriate profile. To add a new version, add a new profile
and a corresponding <repository> declaration:

    ...
    
    <profiles>
        <profile>
            <id>jdg6</id>
            <repositories>
                <repository>
                    <id>jboss-datagrid-6.6.0-maven-repository</id>
                    <url>file:///Users/ovidiu/runtime/jboss-datagrid-6.6.0-maven-repository</url>
                </repository>
            </repositories>
            <properties>
                <infinispan-version>6.4.0.Final-redhat-4</infinispan-version>
            </properties>
        </profile>
        <profile>
            <id>jdg7</id>
            <repositories>
                <repository>
                    <id>jboss-datagrid-7.0-maven</id>
                    <url>file:///Users/ovidiu/runtime/jboss-datagrid-7.0.0-maven-repository/maven-repository</url>
                </repository>
            </repositories>
            <properties>
                <infinispan-version>8.3.0.Final-redhat-1</infinispan-version>
            </properties>
        </profile>
    </profiles>
    
    ...
    
Then you can build:
    
    mvn clean package [-Pjdg6]

The ./bin/hotrod-client wrapper must also be updated to point to the JDG server deployment we're going to connect
to:

    jboss_home=~/runtime/jboss-datagrid-6.6.0-server
    infinispan_version=6.4.0.Final-redhat-4

## Usage Example

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

   
## See Also

https://kb.novaordis.com/index.php/Infinispan_Tools#Tools
 
