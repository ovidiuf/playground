#!/usr/bin/env bash

helm dependency update ./a
helm install --debug --name config-example ./a


