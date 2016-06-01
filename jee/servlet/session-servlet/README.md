#Session Servlet

## Overview

A simple JEE servlet that can be deployed within a JEE container and used to test continuity, load balancing, failover, session stickiness, etc. It has been tested to work with WildFly/EAP and with Tomcat.

## Build

    mvn clean package
    
## Deploy
    
Copy ./target/session-servlet.war into the deployment directory of the application server.
    
## Authentication
    
The default build produces a servlet that *does NOT require authentication*.
    
If you want authentication, do this (JBoss 5 procedure, may need to be updated for WildFly):
    
1. Un-comment web.xml section starting with &lt;security-constraint&gt; and ending with &lt;/security-role&gt;
    
2. Replace "admin" with a valid role. The replacement must be done in both places where &lt;role-name&gt; is mentioned. For example, if deployed on JBoss 5, pick up an appropriate role from $JBOSS_HOME/server/$JBOSS_PROFILE/conf/props/jmx-console-roles.properties.
    
3. Enable &lt;security-domain&gt; in jboss-web.xml and make sure it points to the correct one on the server.
    
## HTTP Session

The smoke servlet will NOT establish a HTTP session by default.

If you wish it to establish a session, call it with "establish-session" parameter on the first request.

Example: http://locahost:8080/session-servlet?establish-session

After the first request, obviously there's no need for "establish-session" anymore, the browser/server ensemble maintain the one that was established. The current implementation will throw an exception if it sees "?establish-session" again.

In order to store a key/value pair into the session, use http://locahost:8080/session-servlet/put?key=something&value=somethingelse. In order to retrieve a key/value pair from the session, use http://locahost:8080/session-servlet/get?key=something 

### Enable HTTP Session Replication

##Root Context

You can change the root context as follows:

###On JBoss

####Method One

Simply deploy the WAR under the desired name.

####Method Two

TODO: use jboss-web.xml and root-context.

###On Tomcat

Simply deploy the WAR under the desired name.

##Test Plan

###1. Simple Availability

Build and deploy. 

Go to http://&lt;server-address&gt;:&lt;server-port&gt;/session-servlet

It will return a simple HTTP page listing relevant information regarding the execution.

###2. Session Experiments

Establish a session with http://&lt;server-address&gt;:&lt;server-port&gt;/session-servlet?establish-session

Then drop the parameter, the browser/server should maintain the session until it expires.


    



