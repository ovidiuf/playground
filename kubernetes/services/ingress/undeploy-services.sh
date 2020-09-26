#!/usr/bin/env bash

namespace=$(kubectl get pod --all-namespaces -l function=serves-http,affiliation=a -o jsonpath="{.items[*].metadata.namespace}")

kubectl -n ${namespace} delete service a
kubectl -n ${namespace} delete service b
kubectl -n ${namespace} delete pod httpd-a --grace-period=0 --force
kubectl -n ${namespace} delete pod httpd-b --grace-period=0 --force
kubectl -n ${namespace} delete ingress example

echo "service and pods undeployed from namespace ${namespace}"