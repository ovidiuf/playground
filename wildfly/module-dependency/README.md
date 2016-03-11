#WildFly Module Dependency Example

#Overview

A project that builds a simple WAR dependent on a custom WildFly module. In order 
to work, it needs access to https://github.com/NovaOrdis/playground/tree/master/wildfly/custom-module
artifacts and it also needs the custom module deployed in the target WildFly instance.


#Related

https://kb.novaordis.com/index.php/Servlet

#Build

```
mvn clean install
```

#Deploy

Update ./bin/deploy configuration and run:

```
./bin/deploy
```


