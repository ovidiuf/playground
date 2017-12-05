#!/bin/bash
#
# Must receive the following via environment variables:
#
# SUBJECT_NAME
#

while [ true ]; do
  echo ${cnt} ${SUBJECT_NAME}
  cnt=$(expr ${cnt} + 1)
  sleep 1
done
