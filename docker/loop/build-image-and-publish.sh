#!/usr/bin/env bash

publish=false
[[ $1 = "-p" ]] && publish=true

docker build -t docker.io/ovidiufeodorov/loop:latest .

if ${publish}; then 
  docker push docker.io/ovidiufeodorov/loop:latest
fi
