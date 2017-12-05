#!/bin/bash

while [ true ]; do
  echo ${cnt}
  cnt=$(expr ${cnt} + 1)
  sleep 1
done
