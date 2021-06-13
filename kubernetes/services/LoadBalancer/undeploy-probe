#!/usr/bin/env bash

line=$(kubectl get pods --all-namespaces | grep probe)
[[ -z ${line} ]] && { echo "[warning]: no probe pod found in any namespace"; exit 0; }
namespace=${line%% *}

kubectl -n ${namespace} delete pod probe --grace-period=0 --force && echo "probe undeployed from namespace ${namespace}"



