#!/usr/bin/env bash

namespace=$1
[[ -z ${namespace} ]] && namespace=default

kubectl -n ${namespace} apply -f ./service-headless.yaml
kubectl -n ${namespace} apply -f ./pod-first.yaml
kubectl -n ${namespace} apply -f ./pod-second.yaml
kubectl -n ${namespace} apply -f ./pod-third.yaml

echo "deployed in namespace ${namespace}"
