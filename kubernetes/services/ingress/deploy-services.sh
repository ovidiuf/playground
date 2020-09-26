#!/usr/bin/env bash

namespace=$1
[[ -z ${namespace} ]] && namespace=default

kubectl -n ${namespace} apply -f ./service-a.yaml
kubectl -n ${namespace} apply -f ./service-b.yaml
kubectl -n ${namespace} apply -f ./pod-service-a.yaml
kubectl -n ${namespace} apply -f ./pod-service-b.yaml
kubectl -n ${namespace} apply -f ./ingress.yaml

echo "deployed in namespace ${namespace}"
