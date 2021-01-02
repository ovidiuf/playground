#!/bin/bash
#
# Must receive the following via environment variables:
#
# SUBJECT_NAME
#

#
# The docker runtime sends a TERM when it wants to gracefully shut down the container
#
function handle-TERM() {

    echo "SIGTERM received ..."
    exit 0
}

#
# On Ctrl-C
#
function handle-INT() {

    echo "SIGINT received ..."
    exit 0
}

function main() {

    trap handle-TERM TERM
    trap handle-INT INT

    cnt=0
    local content

    while [[ true ]]; do

        content=${cnt}

        [[ -n ${SUBJECT_NAME} ]] && content="${content}, ${SUBJECT_NAME}"

        echo "stdout: ${content}"
        echo "sterr:  ${content}" 1>&2

        cnt=$(expr ${cnt} + 1)
        sleep 1
    done
}

main "$@"
