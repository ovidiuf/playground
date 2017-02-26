#Basic Authentication

#Overview

A servlet protected with Basic Authentication.

#Related

https://kb.novaordis.com/index.php/Web_Application_Security#BASIC_Authentication

#Build

```
mvn clean install
```

#Configure the JBoss Instance

The servlet is configured to get the authentication information from the "other" security domain. 
The sample user has to be added to the ApplicationRealm, as part of the "test-role" group. 

#Deploy

Update ./bin/deploy configuration and run:

```
./bin/deploy
```


