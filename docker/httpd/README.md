# httpd Container

A container that can be used to stand up a httpd-based web server. The container comes with network troubleshooting
tools (hostname, ping, curl, nslookup, wget).    

The container is publicly available as [docker.io/ovidiufeodorov/httpd:latest](https://hub.docker.com/r/ovidiufeodorov/httpd).

Build and publish (both commands can be run with `./publish.sh`):

```bash
docker build -t docker.io/ovidiufeodorov/httpd:latest .
docker push docker.io/ovidiufeodorov/httpd:latest
```

Run in foreground: 

```bash
docker run docker.io/ovidiufeodorov/httpd:latest
```


  