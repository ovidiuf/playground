# Loop Pod

A singleton pod based on docker.io/ovidiufeodorov/loop:latest.

To deploy:

```bash
./deploy-pod.sh
```

To remove:

```bash
./delete-pod.sh
```
To log into the container:

```bash
kubectl exec -it loop bash
```
