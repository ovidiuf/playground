#!/bin/bash
#
# Applies the novaordis-cicd template
#

oc process -f $(dirname $0)/novaordis-cicd.yaml | oc create -f -
