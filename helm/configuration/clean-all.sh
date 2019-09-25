#!/usr/bin/env bash


helm delete --purge config-example

$(dirname $0)/clean-dependencies.sh


