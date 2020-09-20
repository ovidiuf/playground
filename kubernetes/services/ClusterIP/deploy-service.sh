#!/usr/bin/env bash

namespace=$1
[[ -z ${namespace} ]] && { echo "[error]: namespace must be specified" 1>&2; exit 1; }
kubectl -n ${namespace} apply -f ./service-clusterIP.yaml
kubectl -n ${namespace} apply -f ./pod-first.yaml
kubectl -n ${namespace} apply -f ./pod-second.yaml
kubectl -n ${namespace} apply -f ./pod-third.yaml
