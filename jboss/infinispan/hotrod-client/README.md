#Infinispan HotRod Client

##Overview

A simple interactive client that connects to an Infinispan/JDG cluster over the HotRod protocol. The "connect" command
creates a RemoteCacheManager and retrieves the default cache. Once the connection is established, the client allows to 
interactively read and write the cache.

##Build

Access to a JDG server deployment is required. Update the value of the "jboss.home" pom.xml system property with 
the path to the JDG server deployment. 

    ...
    
    <properties>
        ...
        <jboss.home>${user.home}/runtime/jboss-datagrid-6.6.0-server</jboss.home>
        <infinispan-version>6.4.0.Final-redhat-4</infinispan-version>
        ...
    </properties>
    
    ...
    
Then you can build:
    
    mvn clean package

The ./bin/hotrod-client wrapper must also be updated to point to the same JDG server deployment:

    jboss_home=~/runtime/jboss-datagrid-6.6.0-server
    infinispan_version=6.4.0.Final-redhat-4

##Usage Example

In order to use the example, you will need to build it first, see above.

    cd .../hotrod-client
    ./bin/ihotrod-client
    > connect localhost:11222
    > put test-key test-value
    > get test-key
    test-value
    > exit
   
##See Also

https://kb.novaordis.com/index.php/Infinispan_Tools#Tools
 
