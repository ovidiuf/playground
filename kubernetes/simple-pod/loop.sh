#!/bin/bash
#
# Must receive the following via environment variables:
#
# SUBJECT_NAME
#

cnt=0

while [ true ]; do
  echo "${cnt}"
  cnt=$(expr ${cnt} + 1)
  sleep 1
done
