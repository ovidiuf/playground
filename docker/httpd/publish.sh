#!/usr/bin/env bash

docker build -t docker.io/ovidiufeodorov/httpd:latest .
docker push docker.io/ovidiufeodorov/httpd:latest
