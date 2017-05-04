# JDG Remote Client/Server Mode access from EAP

# Overview

This is an example of an JDG cluster can be access programatically from a 
JEE container (servlet in this case), using the remote client/server mode.

For more details see:

https://kb.novaordis.com/index.php/JDG_Remote_Client-Server_Mode_Usage_Example

# Build

```
mvn clean install
```

# Use

````
curl http://<eap-node-address>:8080/jdg-access/
````
