# Signal-Aware Loop Container    

A container based on centos that loops and displays an incrementing counter at stdout and stderr. The container
reacts to SIGTERM and SIGINT.
 
Once the container runs, it can be used to log into with: 

```bash
docker exec -it <containerid> bash
```
  
The container is publicly available as [docker.io/ovidiufeodorov/loop:latest](https://hub.docker.com/r/ovidiufeodorov/loop).

Build and publish:

```bash
docker build -t docker.io/ovidiufeodorov/loop:latest .
docker push docker.io/ovidiufeodorov/loop:latest
```

Run in foreground: 

```bash
docker run docker.io/ovidiufeodorov/loop:latest
```


  