# Overview

A simple REST service that can be deployed in openshift and respond to a
https://<public-address>/rest-service/ping.

The image build process is handled by 'it'.

The OpenShift deployment is relies on the embedded template and it can be
triggered with the 'deploy' script, as described below.

The container specification comes with a liveness and readiness probes,
(/opt/live and /opt/ready). 

# Build Image

  cd image
  it
  
This will publish the image on Docker Hub as docker.io/novaordis/rest-service:latest  

# Deploy to OpenShift 

    oc login
    oc project <target-project>
    oc process -f ./openshift-template.yaml | oc create -f -
    
    
# Invoke the Service(s)

    call A command command-args    

# Notes
