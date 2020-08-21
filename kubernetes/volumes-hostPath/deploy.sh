#!/usr/bin/env bash

d=/Users/ovidiufeodorov/tmp/httpd-root
[[ -d ${d} ]] || { mkdir ${d} && echo "created ${d}"; }

kubectl apply -f ./pod-with-hostPath-volume.yaml
