# httpd Serving Local hostPath

## NOKB

[httpd Deploy in Kubernetes](https://kb.novaordis.com/index.php/Httpd_Deploy_in_Kubernetes#Overview)

## Overview

A local httpd instance that serves a host directory.

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
kubectl logs -f httpd
```

### Log into the container
 
```bash
kubectl exec -it httpd bash
```
