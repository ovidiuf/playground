# Overview

The infrastructure required to build a container based on an OpenShift.
JBoss image, that runs a generic REST service.

The service responds to "start", "ping". 

The service can also be started automatically by declaring REST_SERVICE_START=true in environment.

The image build process is handled by 'it'.

The project also includes the OpenShift template to deploy the image.

# Build Image

  cd image
  it
  
This will publish the image on Docker Hub as docker.io/novaordis/rest-service:latest  

# Deploy to OpenShift 

    oc login
    oc project <target-project>
    oc process -f ./openshift-template.yaml | oc create -f -
    
# Invoke the Service(s)

    call ping
    call start
    
        

# Notes
