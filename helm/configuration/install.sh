#!/usr/bin/env bash

helm dependency update ./a

#helm install --name config-example ./a

#helm install --debug --name config-example -f ./file-based-overrides.yaml ./a
#helm install --debug --name config-example --set-file ./set-file-example.yaml ./a
#helm install --name config-example --set color=null ./a
#helm install --name config-example --set color=gray --set b.color=alabaster --set size=11 ./a
#helm install --name config-example --set color=gray --set b.color=alabaster --set-string size=12 ./a
#helm install --name config-example --set color=gray --set b.color=alabaster --set-string size=12 --set components.engine.capacity=65 ./a

helm install --debug --name config-example --set shapes="{rectangle,triangle}" ./a


