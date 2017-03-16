#SSL Servlet

#Overview

An example of a simple servlet requesting SSL/TLS transport layer security via
its web.xml. The servlet does not request any kind of authentication, just that
the transport is secure. 

#Related

https://kb.novaordis.com/index.php/Web_Application_Security#.3Cuser-data-constraint.3E.2C_.3Ctransport-guarantee.3E

#Build

```
mvn clean install
```

#Configure the JBoss Instance

The JBoss instance must be configured to expose a HTTPS connector (HTTPS undertow listener).

#Test

http://localhost:8080/ssl-servlet 

The server should redirect to https://localhost:8443/ssl-servlet 


