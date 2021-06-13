#!/usr/bin/env bash

namespace=$1
[[ -z ${namespace} ]] && { echo "[error]: namespace must be specified" 1>&2; exit 1; }

kubectl -n ${namespace} apply -f ./pod-probe.yaml
