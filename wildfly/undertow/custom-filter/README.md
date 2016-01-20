#Custom Undertow Filter Example

#Overview

A simple custom Undertow filter example that deploys as a WildFly module and it is configured in the &lt;filters&gt;
section of the Undertow subsystem's configuration. The filter measures request time, and the implementation is 
interesting because the start watch event happens on a different thread than the one that triggers the stop watch 
event, unlike in Tomcat's case, where a filters' pre-invocation and post-invocation code is executed on the same
thread.

#Related

https://kb.novaordis.com/index.php/Configuring_a_Custom_Undertow_Filter_in_WildFly

#Build

```
mvn clean install
```

#Deploy

Update ./bin/deploy configuration and run:

```
./bin/deploy
```


