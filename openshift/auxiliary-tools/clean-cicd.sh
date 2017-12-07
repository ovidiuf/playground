#!/bin/bash
#
# Cleans the specified project
#

[ -f $(dirname $0)/../oc-bash/functions ] && . $(dirname $0)/../oc-bash/functions || { echo "$(dirname $0)/../oc-bash/functions not found" 1>&2; exit 1; }

project_name=cicd

function clean-storage() {

    local pvc_name=$1
    [ -z "${pvc_name}" ] && { echo "'pvc_name' not specified" 1>&2; exit 1; }

    pv_name=$(get-persistent-volume-name ${pvc_name})

    oc-delete pvc/${pvc_name}

    if [ -z "${pv_name}" ]; then
        echo "WARN: no persistent volume name found for persistent volume claim ${pvc_name}" 1>&2;
    else
        echo "unbinding ${pv_name}"
        unbind-persistent-volume ${pv_name}
    fi
}

function get-persistent-volume-name() {

    local pvc_name=$1
    [ -z "${pvc_name}" ] && { echo "'pvc_name' not specified" 1>&2; exit 1; }

    local pv_name

    if oc get pvc/${pvc_name} > /dev/null 2>&1; then

        pv_name=$(oc get --template '{{.spec.volumeName}}{{"\n"}}' pvc/${pvc_name}) || { echo "failed to execute oc get --template" 1>&2; exit 1; }
    fi

    #
    # may return ""
    #

    echo "${pv_name}"
}

function unbind-persistent-volume() {

    local pv_name=$1
    [ -z "${pv_name}" ] && { echo "'pv_name' not specified" 1>&2; exit 1; }

    oc patch pv/${pv_name} --patch='{ "spec": { "claimRef": null}}' && \
        echo "${pv_name} unbound - CLEAN ${pv_name} MANUALLY: rm -r /nfs/${pv_name}/*" || \
        { echo "failed to unbind ${pv_name} from its claim" 1>&2; exit 1; }
}

function clean-gogs() {

    oc-delete routes/gogs
    oc-delete dc/postgresql-gogs
    oc-delete svc/postgresql-gogs

    oc-delete dc/gogs
    oc-delete is/gogs
    oc-delete svc/gogs

    oc-delete cm/gogs-config
    oc-delete cm/gogs-install

    oc-delete po/install-gogs

    clean-storage gogs-config
    clean-storage gogs-data
    clean-storage postgresql-gogs-data
}

function clean-nexus() {

    oc-delete routes/nexus
    oc-delete dc/nexus
    oc-delete svc/nexus
    oc-delete is/nexus

    clean-storage nexus
}

function clean-sonarqube() {

    oc-delete routes/sonarqube
    oc-delete dc/sonarqube
    oc-delete svc/sonarqube
    oc-delete is/sonarqube
}

function clean-jenkins-slaves-config-map() {

    oc-delete cm/jenkins-slaves
}

function clean-build-configuration() {

    oc-delete bc/novaordis-pipeline
}


function main() {

    verify-project-name ${project_name}

#    clean-gogs
#
#    clean-nexus
#
#    clean-sonarqube
#
#    clean-jenkins-slaves-config-map
#
    clean-build-configuration
}

main $@
