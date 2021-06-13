#!/usr/bin/env bash

line=$(kubectl get svc --all-namespaces | grep example)
[[ -z ${line} ]] && { echo "[warning]: no 'example' service found in any namespace"; exit 0; }
namespace=${line%% *}

kubectl -n ${namespace} delete service example
kubectl -n ${namespace} delete pod httpd --grace-period=0 --force
kubectl -n ${namespace} delete pod httpd-2 --grace-period=0 --force
kubectl -n ${namespace} delete pod httpd-3 --grace-period=0 --force

echo "service and pods undeployed from namespace ${namespace}"