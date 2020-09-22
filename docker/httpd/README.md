# httpd Container

## Overview

A container that can be used to stand up a httpd-based web server. The container comes with network 
troubleshooting tools (hostname, ping, curl, nslookup, wget). 

The httpd server serves by default the content from /usr/local/apache2/htdocs, which is an index.html
that reports the IP address of the container, as returned by:

```
ip addr show dev eth0 | grep inet | awk '{print $2}'
```    

The container is publicly available as [docker.io/ovidiufeodorov/httpd:latest](https://hub.docker.com/r/ovidiufeodorov/httpd).

## Configuration

###Port

The httpd binds by default on port 80. To change this, inject HTTPD_PORT=<port> environment variable in the container's environment.

##Run in Foreground 
 
 ```bash
docker run docker.io/ovidiufeodorov/httpd:latest
 ```

## Build and Publish

Build and publish (both commands can be run with `./publish.sh`):

```bash
docker build -t docker.io/ovidiufeodorov/httpd:latest .
docker push docker.io/ovidiufeodorov/httpd:latest
```

  