# JDG Library Mode access from EAP

# Overview

This is an example of how a JEE application (servlet in this case) can bootstrap and 
programmatically use a JDG cluster in library mode.
 
For more details see:

https://kb.novaordis.com/index.php/JDG_in_Library_Mode_Usage_Example

# Build

```
mvn clean install
```

# Use

````
curl http://<eap-node-address>:8080/jdg-access/put/Some-Key/Some-Value
curl http://<eap-node-address>:8080/jdg-access/get/Some-Key
````
