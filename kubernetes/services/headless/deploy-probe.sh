#!/usr/bin/env bash

namespace=$1
[[ -z ${namespace} ]] && namespace=default

kubectl -n ${namespace} apply -f ./pod-probe.yaml

echo "deployed in namespace ${namespace}"
