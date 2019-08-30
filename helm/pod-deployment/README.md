# Pod Deployment

Deploys a simple pod based on a loop container. By default, the pod is deployed
in a "blue" namespace, which is also created, but if you want to deploy in the 
default namespace, change pod's metadata.namespace to "default" or delete it.

To build the image the pod needs go to playground/kubernetes/simple-pod and run:

```bash
./build-image.sh
```