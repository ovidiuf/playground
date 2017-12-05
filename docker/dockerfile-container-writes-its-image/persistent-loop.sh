#!/bin/bash
#
# Must receive the following via environment variables:
#
# SUBJECT_NAME
#

#
# read the value of the counter from the filesystem
#

[ -f /tmp/counter ] && cnt=$(cat /tmp/counter)
[ -z "${cnt}" ] && cnt=0

while [ true ]; do
  echo "${cnt}"
  cnt=$(expr ${cnt} + 1)
  echo ${cnt} > /tmp/counter
  sleep 1
done
