# httpd Pod and Service

The simplest possible infrastructure (naked httpd pod and service) that stands up a web server to answer
requests.

## Deployment

```bash
./deploy.sh
```
After components settle, the web server can be accessed at http://localhost/

## Undeployment

```bash
./undeploy.sh
```

## Operations

### Invoke

```bash
curl http://localhost
```

### Logs

```bash
kubectl logs -f httpd-pod
```

### Log into the container
 
```bash
kubectl exec -it httpd-pod bash
```
