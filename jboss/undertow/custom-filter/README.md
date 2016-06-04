#Custom Undertow Filter Example

#Overview

A simple custom Undertow filter example that deploys as a WildFly module and it is configured in the &lt;filters&gt;
section of the Undertow subsystem's configuration. The filter measures request time, and the implementation is 
interesting because the start watch event happens on a different thread than the one that triggers the stop watch 
event, unlike in Tomcat's case, where a filters' pre-invocation and post-invocation code is executed on the same
thread.

#More Details

https://kb.novaordis.com/index.php/Configuring_a_Custom_Undertow_Filter_in_WildFly

#Build

```
mvn clean install
```
#WildFly Configuration

```
        <subsystem xmlns="urn:jboss:domain:undertow:3.0">
            <buffer-cache name="default"/>
            <server name="default-server">
                <http-listener name="default-http-listener" redirect-socket="https" worker="undertow-xnio-worker" socket-binding="http"/>
                <host name="default-host" alias="localhost">
                    <location name="/" handler="welcome-content"/>
                    <filter-ref name="custom-filter"/>
                    <filter-ref name="server-header"/>
                    <filter-ref name="x-powered-by-header"/>
                </host>
            </server>
            <servlet-container name="default">
                <jsp-config/>
                <websockets/>
            </servlet-container>
            <handlers>
                <file name="welcome-content" path="${jboss.home.dir}/welcome-content"/>
            </handlers>
            <filters>
                <response-header name="server-header" header-value="JBoss-EAP/7" header-name="Server"/>
                <response-header name="x-powered-by-header" header-value="Undertow/1" header-name="X-Powered-By"/>
                <filter name="custom-filter" module="com.novaordis.playground.wildfly.undertow.customfilter:1" class-name="com.novaordis.playground.wildfly.undertow.customfilter.ResponseTimeHandler">
                    <param name="param1" value="value1"/>
                </filter>
            </filters>
        </subsystem>

```

#Deploy

Update ./bin/deploy configuration and run:

```
./bin/deploy
```


