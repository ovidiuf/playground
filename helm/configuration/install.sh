#!/usr/bin/env bash

helm dependency update ./a
helm install --debug --name config-example -f ./file-based-overrides.yaml ./a
#helm install --debug --name config-example --set-file ./set-file-example.yaml ./a


