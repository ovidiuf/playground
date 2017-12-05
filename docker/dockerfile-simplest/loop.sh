#!/bin/bash
#
# Must receive the following via environment variables:
#
# SUBJECT_NAME
#

echo_content=${SUBJECT_NAME}
[ -z "${echo_content}" ] && echo_content="no 'SUBJECT_NAME' environment variable defined"

while [ true ]; do
  echo ${cnt} ${echo_content}
  cnt=$(expr ${cnt} + 1)
  sleep 1
done
