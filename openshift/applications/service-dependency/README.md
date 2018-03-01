# Overview

An example of service dependency mechanism based on init containers.

Service A depends on B and its container won't be deployed until Service B
starts. The deployment is being delayed by the Service A's init container,
that polls B.

Both A and B are based on a simple REST service that can be deployed in 
OpenShift and responds to https://<public-address>/rest-service/ping. 
The service responds only if it was started with
https://<public-address>/rest-service/start. If the service is not started, 
the response is 403 (Forbidden). The service can also be started automatically
by declaring REST_SERVICE_START=true in environment.

The image build process is handled by 'it'.

The OpenShift deployment is relies on the embedded template openshift-template.yaml

# Build Image

  cd image
  it
  
This will publish the image on Docker Hub as docker.io/novaordis/rest-service:latest  

# Deploy to OpenShift 

    oc login
    oc project <target-project>
    oc process -f ./openshift-template.yaml | oc create -f -
    
# Invoke the Service(s)

    call b start
    
        

# Notes
