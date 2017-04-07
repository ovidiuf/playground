#Application Component Environment and java:comp

#Overview

A project that allows experimentation with java:comp

Must be deployed in a standalone-full instance, as it looks up JMS destinations and ConnectionFactories.

The following declarations are needed in standalone-full.xml

````
<subsystem xmlns="urn:jboss:domain:naming:1.4">
    <bindings>
        <simple name="java:global/simple-global-int-example" value="10" type="java.lang.Integer"/>
        <lookup name="java:/Mapped-Expiry-Queue" lookup="java:/jms/queue/ExpiryQueue"/>
    </bindings>
</subsystem>
````
