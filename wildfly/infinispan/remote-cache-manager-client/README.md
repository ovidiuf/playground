#Remote Cache Manager Client

##Overview

A simple interactive client that connects to an Infinispan/JDG cluster over the HotRod protocol. The "connect" command
creates a RemoteCacheManager and retrieves the default cache. Once the connection is established, the client allows to 
interactively read and write the cache.

##Build

The JDG Maven repository corresponding to the JDG release you're experimenting with is required. Update the value
of the "" system property in pom.xml with the location of the repository.

    ...
    
    <properties>
        ...
        <jdg.maven.repository>/Users/ovidiu/runtime/jboss-eap-6.4.0.GA-maven-repository</jdg.maven.repository>
        <infinispan-version>8.0.1.Final-redhat-1</infinispan-version>
        ...
    </properties>
    
    ...
    
Then you can build:
    
    mvn clean package

The build command also assembles the classpath required by the ./bin/ic wrapper.

##Usage Example

In order to use the example, you will need to build it first, see above.

    cd .../remote-cache-manager-client
    ./bin/ic
    > connect localhost:11222
    > put test-key test-value
    > get test-key
    test-value
    > exit
    
    
    



