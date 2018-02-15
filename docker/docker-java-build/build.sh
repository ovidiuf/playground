#!/usr/bin/env bash

IMAGE_REGISTRY=docker.io
IMAGE_NAMESPACE=novaordis
IMAGE_REPOSITORY=crone
IMAGE_TAG=latest
JAVA_PROJECT_DIR=
LOCAL_MAVEN_REPOSITORY=/Users/ovidiu/.m2/repository
EXTERNAL_ARTIFACTS=""

function main() {

    local build_java=false
    local build_image=false

    while [ -n "$1" ]; do

        if [ "--image" == "$1" ]; then

            build_image=true
        fi
        shift
    done

    if ! ${build_java} && ! ${build_image}; then
        build_java=true
        build_image=true
    fi

    if ${build_java}; then

        build-java || { echo "failed to build java code" 1>&2; exit 1; }
    fi

    if ${build_image}; then

        build-image || { echo "failed to build image" 1>&2; exit 1; }
    fi

    echo "all ok"
}

function build-java() {

    # if no JAVA_PROJECT_DIR, return
    [ ! -d "${JAVA_PROJECT_DIR}" ] && return 0

    (cd $(dirname $0)/${JAVA_PROJECT_DIR}; mvn clean package)
}

function build-image() {

    fetch-external-artifacts || return 1;

    [ -f $(dirname $0)/Dockerfile ] || { echo "no $(dirname $0)/Dockerfile"; return 1; }

    if ! docker build -t ${IMAGE_REGISTRY}/${IMAGE_NAMESPACE}/${IMAGE_REPOSITORY}:${IMAGE_TAG} .; then
        echo "docker build failed" 1>&2;
        return 1;
    fi
}

function fetch-external-artifacts() {

    [ -z "${EXTERNAL_ARTIFACTS}" ] && return 0;

    for i in ${EXTERNAL_ARTIFACTS}; do
        if [ ! -f $(dirname $0)/artifacts/${i} ]; then
            src=$(find ${LOCAL_MAVEN_REPOSITORY} -name ${i})
            [ -z "${src}" ] && { echo "${i} not found in ${LOCAL_MAVEN_REPOSITORY}" 1>&2; return 1; }
            cp ${src} $(dirname $0)/artifacts && \
                echo "copied ${i} to $(dirname $0)/artifacts" || \
                { echo "failed to copy ${src}" 1>&2; return 1; }
        fi
    done
}

main $@;