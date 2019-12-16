#!/usr/bin/env bash

REPOSITORY_CHART_DIR=/Users/ovidiufeodorov/runtime/httpd-root/charts

chart_name=$1

[[ -z ${chart_name} ]] && { echo "chart name must be provided as the first argument" 1>&2; exit 1; }

[[ -d ${REPOSITORY_CHART_DIR} ]] || { echo "directory ${REPOSITORY_CHART_DIR} does not exit" 1>&2; exit 1; }

command="helm package -d ${REPOSITORY_CHART_DIR} -u $(dirname $0)/${chart_name}"
echo ${command}
${command} && echo "ok" || exit 1

command="helm repo index ${REPOSITORY_CHART_DIR}"
echo ${command}
${command} && echo "ok" || exit 1

