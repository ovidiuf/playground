#!/bin/bash
#
# Must receive the following via environment variables:
#
# SUBJECT_NAME
#

echo_content=${SUBJECT_NAME}
[ -z "${echo_content}" ] && echo_content="no 'SUBJECT_NAME' environment variable defined"
cnt=0

while [ true ]; do
  echo "stdout: ${cnt},  ${echo_content}"
  echo "sterr: ${cnt},  ${echo_content}" 1>&2
  cnt=$(expr ${cnt} + 1)
  sleep 1
done
